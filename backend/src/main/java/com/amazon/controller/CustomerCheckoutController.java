package com.amazon.controller;

import com.amazon.constant.OrderHistoryStatus;
import com.amazon.dto.OrderHistoryDto;
import com.amazon.entity.Account;
import com.amazon.entity.Address;
import com.amazon.entity.OrderHistory;
import com.amazon.entity.Payment;
import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;
import com.amazon.mapper.OrderHistoryMapper;
import com.amazon.mapper.ShoppingItemMapper;
import com.amazon.service.AccountService;
import com.amazon.service.AddressService;
import com.amazon.service.CustomerCheckoutService;
import com.amazon.service.CustomerShoppingService;
import com.amazon.service.PaymentService;
import com.amazon.util.Util;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer/checkout")
public class CustomerCheckoutController {

    private final CustomerCheckoutService customerCheckoutService;
    private final AccountService accountService;
    private final CustomerShoppingService customerShoppingService;
    private final PaymentService paymentService;
    private final AddressService addressService;

    public CustomerCheckoutController(CustomerCheckoutService customerCheckoutService,
                                      AccountService accountService,
                                      CustomerShoppingService customerShoppingService,
                                      PaymentService paymentService,
                                      AddressService addressService) {
        this.customerCheckoutService = customerCheckoutService;
        this.accountService = accountService;
        this.customerShoppingService = customerShoppingService;
        this.paymentService = paymentService;
        this.addressService = addressService;
    }

    @PostMapping("/{shoppingCartId}")
    @Transactional
    public OrderHistoryDto createOrderHistory(@PathVariable int shoppingCartId) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        ShoppingCart shoppingCart = customerShoppingService.findCustomerShoppingCart(account.getId(), shoppingCartId);
        OrderHistory orderHistory;
        try {
            orderHistory = customerCheckoutService.getOrderHistoryByShoppingCartId(account, shoppingCart.getId());
            return OrderHistoryMapper.INSTANCE.map(orderHistory);
        } catch(IllegalArgumentException e) {

        }
        orderHistory = new OrderHistory(account, shoppingCart.getTotalAmount(), null, null, OrderHistoryStatus.IN_PROCESS);
        List<ShoppingItem> shoppingItems = customerShoppingService.findAllShoppingItemsByShoppingCartId(shoppingCart.getId());
        final OrderHistory orderHistoryFinal = customerCheckoutService.saveOrderHistory(orderHistory);
        shoppingItems.forEach(shoppingItem -> shoppingItem.setOrderHistoryId(orderHistoryFinal.getId()));
        customerShoppingService.saveShoppingItems(shoppingItems);
        return OrderHistoryMapper.INSTANCE.map(orderHistory);
    }

    @GetMapping("/{orderHistoryId}")
    @Transactional(readOnly = true)
    public OrderHistoryDto getOrderHistory(@PathVariable Integer orderHistoryId) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        OrderHistoryDto dto = OrderHistoryMapper.INSTANCE.map(customerCheckoutService.findOrderHistoryByAccountAndId(account, orderHistoryId));
        dto.setShoppingItemDtos(ShoppingItemMapper.INSTANCE.map(customerShoppingService.findAllShoppingItemsByOrderHistoryId(orderHistoryId)));
        return dto;
    }

    @PutMapping("/{orderHistoryId}/payment/{paymentId}")
    @Transactional
    public OrderHistoryDto choosePaymentMethodWhenCheckout(@PathVariable int orderHistoryId,
                                                           @PathVariable int paymentId) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        OrderHistory orderHistory = customerCheckoutService.getOrderHistoryByOrderHistoryId(account, orderHistoryId);
        Map<Integer, Payment> paymentMap = paymentService.getAll(account).stream().collect(Collectors.toMap(Payment::getId, payment -> payment));
        if (!paymentMap.keySet().contains(paymentId)) {
            throw new IllegalArgumentException("There's no such payment method for the customer.");
        }
        orderHistory.setPayment(paymentMap.get(paymentId));
        return OrderHistoryMapper.INSTANCE.map(customerCheckoutService.saveOrderHistory(orderHistory));
    }

    @PutMapping("/{orderHistoryId}/address/{addressId}")
    @Transactional
    public OrderHistoryDto chooseAddressWhenCheckout(@PathVariable int orderHistoryId,
                                                     @PathVariable int addressId) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        OrderHistory orderHistory = customerCheckoutService.getOrderHistoryByOrderHistoryId(account, orderHistoryId);
        Map<Integer, Address> addressMap = addressService.getAll(account).stream().collect(Collectors.toMap(Address::getId, address -> address));
        if (!addressMap.keySet().contains(addressId)) {
            throw new IllegalArgumentException("There's no such address for the customer.");
        }
        orderHistory.setAddress(addressMap.get(addressId));
        return OrderHistoryMapper.INSTANCE.map(customerCheckoutService.saveOrderHistory(orderHistory));
    }

    @PutMapping("/{orderHistoryId}")
    @Transactional
    public OrderHistoryDto completeCheckout(@PathVariable int orderHistoryId) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        OrderHistory orderHistory = customerCheckoutService.getOrderHistoryByOrderHistoryId(account, orderHistoryId);
        ShoppingCart shoppingCart = customerShoppingService.findShoppingCartByAccountId(account.getId());
        List<ShoppingItem> shoppingItems = customerShoppingService.findAllShoppingItemsByShoppingCartId(shoppingCart.getId());
        if (shoppingItems.stream().findAny().get().getOrderHistoryId().equals(orderHistoryId)
                && orderHistory.getTotalAmount() == shoppingCart.getTotalAmount()
                && Objects.nonNull(orderHistory.getPayment()) && Objects.nonNull(orderHistory.getAddress())) {
            orderHistory.setStatus(OrderHistoryStatus.COMPLETED);
            orderHistory = customerCheckoutService.saveOrderHistory(orderHistory);
            customerShoppingService.deleteShoppingCart(shoppingCart);
            return OrderHistoryMapper.INSTANCE.map(orderHistory);
        }
        throw new IllegalArgumentException("Checkout failed.");
    }
}
