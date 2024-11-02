package com.example.grocery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.grocery.dto.OrderDto;
import com.example.grocery.exception.StockNotAvailable;
import com.example.grocery.jwt.JwtAuthenticationHelper;
import com.example.grocery.model.Grocery;
import com.example.grocery.model.GroceryDetails;
import com.example.grocery.model.Order;
import com.example.grocery.model.User;
import com.example.grocery.repository.GroceryDetailRepository;
import com.example.grocery.repository.OrderRepository;
import com.example.grocery.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private GroceryDetailRepository groceryDetailRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtAuthenticationHelper jwtAuthenticationHelper;

	public String placeOrder(OrderDto orderDto, String token) {
		token = token.replace("Bearer ", "");
		String userName = jwtAuthenticationHelper.getUsernameFromToken(token);
		User user = userRepository.findByUsername(userName).get();
		List<GroceryDetails> groceryDetailList = getAvailableGrocery();
		List<Grocery> groceryList = orderDto.getGroceries();
		List<GroceryDetails> updateGroceryDetailList = new ArrayList<GroceryDetails>();
		Order order = new Order();
		double totalPrice = 0;
		for (Grocery grocery : groceryList) {
			boolean isGroceryAvailable = false;
			for (GroceryDetails groceryDetails : groceryDetailList) {
				if (grocery.getGroceryId() == groceryDetails.getId()) {
					if (grocery.getQuantity() > groceryDetails.getQuantity()) {
						throw new StockNotAvailable("Available quantity is less than ordered quantity for grocery "
								+ groceryDetails.getName());

					}
					isGroceryAvailable = true;
					updateGroceryDetailList.add(groceryDetails);
					groceryDetails.setQuantity(groceryDetails.getQuantity() - grocery.getQuantity());
					totalPrice += grocery.getQuantity() * groceryDetails.getPrice();
					grocery.setOrder(order);
					break;
				}
			}
			if (!isGroceryAvailable) {
				throw new StockNotAvailable("Stock not available or invalid grocery id : " + grocery.getGroceryId());
			}
		}
		order.setGroceries(groceryList);
		order.setTotalPrice(totalPrice);
		order.setUser(user);
		orderRepository.save(order);
		updateGroceryDetail(updateGroceryDetailList);
		return "Your order total is : " + totalPrice;

	}

	private void updateGroceryDetail(List<GroceryDetails> updateGroceryDetailList) {
		groceryDetailRepository.saveAll(updateGroceryDetailList);

	}

	public List<GroceryDetails> getAvailableGrocery() {
		return groceryDetailRepository.findByQuantityGreaterThan(0);
	}
}
