package persistence.OrderShoe;

public interface OrderShoeDAOInterface 
{
    OrderDTO save(OrderDTO dto);
    OrderDTO findById(Long id);
}
