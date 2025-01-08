package core.base.item;

public record ItemType(
        String item1,
        String item2
){
    public static ItemType of(String item1, String item2){
        return new ItemType(item1, item2);
    }
}

