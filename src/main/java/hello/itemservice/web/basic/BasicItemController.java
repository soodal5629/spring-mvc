package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //@PostMapping("/add")
    public String addItemV4(Item item){
        // @ModelAttribute 생략 가능
        itemRepository.save(item);
        // model.addAttribute("item", item); --> @ModelAttribute 사용하면 얘도 자동으로 해주므로 생략 가능
        return "basic/item"; // <-- 새로고침하면 문제
    }

    //@PostMapping("/add")
    public String addItemV5(Item item){
        // @ModelAttribute 생략 가능
        itemRepository.save(item);
        // model.addAttribute("item", item); --> @ModelAttribute 사용하면 얘도 자동으로 해주므로 생략 가능
        return "redirect:/basic/items/"+item.getId(); // 리다이렉트 해야함
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        // @ModelAttribute 생략 가능
        Item savedItem = itemRepository.save(item);
        // redirectAttributes 쓰면 기본적인 url 인코딩같은 것이 해결됨
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true); // 얘는 쿼리 파라미터로 넘어감

        // model.addAttribute("item", item); --> @ModelAttribute 사용하면 얘도 자동으로 해주므로 생략 가능
        return "redirect:/basic/items/{itemId}"; // redirectAttributes 에 넣은 값이 itemId에 들어감
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; //@PathVariable 에 있는것도 여기서 쓸 수 있음
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
