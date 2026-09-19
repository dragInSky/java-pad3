package MyFirstTestAppSpringBoot.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class HelloController {

    private ArrayList<String> arrayList;
    private HashMap<Integer, String> hashMap;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",
            defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam(value = "s") String s) {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(s);
        return String.format("Значение \"%s\" добавлено в ArrayList", s);
    }

    @GetMapping("/show-array")
    public ArrayList<String> showArrayList() {
        return arrayList == null ? new ArrayList<>() : arrayList;
    }

    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam(value = "s") String s) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
        }
        hashMap.put(hashMap.size(), s);
        return String.format("Значение \"%s\" добавлено в HashMap", s);
    }

    @GetMapping("/show-map")
    public HashMap<Integer, String> showHashMap() {
        return hashMap == null ? new HashMap<>() : hashMap;
    }

    @GetMapping("/show-all-lenght")
    public String showAllLenght() {
        int arraySize = arrayList == null ? 0 : arrayList.size();
        int mapSize = hashMap == null ? 0 : hashMap.size();
        return String.format("Количество элементов в ArrayList: %d, в HashMap: %d",
                arraySize, mapSize);
    }
}
