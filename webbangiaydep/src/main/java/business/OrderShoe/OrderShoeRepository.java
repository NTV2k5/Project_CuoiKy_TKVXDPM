package business.OrderShoe;

import business.entity.Order;

public interface OrderShoeRepository 
{
    Order save(Order order);
    Order findById(Long id);
}
