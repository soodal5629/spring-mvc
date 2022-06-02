package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    // 멀티 쓰레드가 접근한다고 생각하면 ConcurrentHashMap과 AutomicLong 등을 써야 함!!!!
    private static final Map<Long, Item> store = new HashMap<>(); // 싱글톤 --> static
    private static long sequence = 0L; // 싱글톤 --> static

    public Item save(Item item){
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id){
        return store.get(id);
    }

    public List<Item> findAll(){
        // store 데이터에 직접적인 영향 없도록 ArrayList에 안전하게 한번 감싸서 return 한것
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam){ // 원래는 dto 따로 만드는 것이 정석임
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}
