package persistence.Adapter;

import business.OrderShoe.OrderShoeRepository;
import business.entity.Order;
import business.entity.OrderItem;

import persistence.OrderShoe.OrderDTO;
import persistence.OrderShoe.OrderItemDTO;
import persistence.OrderShoe.OrderShoeDAOInterface;
import java.util.stream.Collectors;
public class OrderShoeRepositoryImpl implements OrderShoeRepository {

    private final OrderShoeDAOInterface dao;
    public OrderShoeRepositoryImpl(OrderShoeDAOInterface dao) {
        this.dao = dao;
    }

    @Override
    public Order save(Order order) {
        OrderDTO dto = toDto(order);
        OrderDTO savedDto = dao.save(dto);
        return toEntity(savedDto);
    }

    @Override
    public Order findById(Long id) {
        OrderDTO dto = dao.findById(id);
        return dto != null ? toEntity(dto) : null;
    }

    // === CHUYỂN TỪ ENTITY → DTO ===
    private OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setUserId(order.getUserId());
        dto.setCustomerName(order.getCustomerName());
        dto.setEmail(order.getEmail());
        dto.setPhone(order.getPhone());
        dto.setAddress(order.getAddress());
        dto.setStatus(order.getStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setNote(order.getNote());
        dto.setTotal(order.getTotal());

        dto.setItems(order.getItems().stream().map(item -> {
            OrderItemDTO itemDto = new OrderItemDTO();
            itemDto.setProductId(item.getProductId());
            itemDto.setVariantId(item.getVariantId());
            itemDto.setProductName(item.getProductName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            return itemDto;
        }).collect(Collectors.toList()));

        return dto;
    }

    // === CHUYỂN TỪ DTO → ENTITY ===
    private Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setUserId(dto.getUserId());
        order.setCustomerName(dto.getCustomerName());
        order.setEmail(dto.getEmail());
        order.setPhone(dto.getPhone());
        order.setAddress(dto.getAddress());
        order.setStatus(dto.getStatus());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setNote(dto.getNote());
        order.setTotal(dto.getTotal());

        if (dto.getCreatedAt() != null) {
            order.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            order.setUpdatedAt(dto.getUpdatedAt());
        }

        order.setItems(dto.getItems().stream().map(dtoItem -> {
            OrderItem item = new OrderItem();
            item.setProductId(dtoItem.getProductId());
            item.setVariantId(dtoItem.getVariantId());
            item.setProductName(dtoItem.getProductName());
            item.setQuantity(dtoItem.getQuantity());
            item.setUnitPrice(dtoItem.getUnitPrice());
            return item;
        }).collect(Collectors.toList()));

        return order;
    }
}