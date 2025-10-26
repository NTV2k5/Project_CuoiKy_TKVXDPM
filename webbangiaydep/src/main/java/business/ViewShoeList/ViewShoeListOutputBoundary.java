package business.ViewShoeList;

import java.util.List;
import persistence.ViewShoeList.ViewShoeListDTO;


public interface ViewShoeListOutputBoundary {
    void presentShoeList(List<ViewShoeListDTO> shoes);
}
