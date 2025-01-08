package core.base.container;


public enum GroundContactRule {

    // Free/unlimited stacking, A part of an item can hang over a stack
    FREE,
    // An item must stand on the floor or other items for all 4 floor corners
    COVERED,
    // An item must be stacked only upon one other item
    SINGLE,
    // An item can stand on the ground or must cover multiple items
    MULTIPLE
}
