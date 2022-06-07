package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final 붙은 애를 가지고 생성자 만들어줌(Autowired 해줌)
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        model.addAttribute("temp", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        // model.addAttribute("item", item); --> @ModelAttribute 사용하면 얘도 자동으로 해주므로 생략 가능
        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        // 클래스명의 첫글자를 소문자로 바꾼것이 @ModelAttribute 에 적용됨 (Item --> item)
        itemRepository.save(item);
        // model.addAttribute("item", item); --> @ModelAttribute 사용하면 얘도 자동으로 해주므로 생략 가능
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV4(Item item){
        // @ModelAttribute 생략 가능
        itemRepository.save(item);
        // model.addAttribute("item", item); --> @ModelAttribute 사용하면 얘도 자동으로 해주므로 생략 가능
        return "basic/item";
    }
    /*
    * 테스트용 데이터 추가
    */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 50000, 20));
    }



}