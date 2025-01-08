package core.opt.construction


import helper.Helper
import spock.lang.Ignore
import spock.lang.Specification
import core.base.GRASPModel
import core.base.GRASPParameter
import core.base.container.GroundContactRule
import core.base.container.ParameterType
import core.base.item.Item
import core.opt.improvement.LocalSearchPacker

class LocalSearchPackerSpec extends Specification {

    def service = new LocalSearchPacker()

    def "hard but solvable"() {
        def items = new ArrayList<Item>()
        for (int i = 0; i < 9; i++)
            items.add(Helper.getItem(2,1,1,1,4,0))
        for (int i = 0; i < 18; i++)
            items.add(Helper.getItem(1,1,1,1,3,0))

        Collections.shuffle(items, new Random(1234))
        GRASPModel model = new GRASPModel(
                items.toArray(new Item[0]),
                [Helper.getAddSpaceContainer2(4,3,3)] as core.base.container.Container[],
                new GRASPParameter(),
                Helper.getStatusManager()
        )
        model.containerTypes[0].parameter.add(ParameterType.GROUND_CONTACT_RULE, GroundContactRule.COVERED)

        when:
        service.execute(model)
        then:
        model.containers.length == 1
        items.find {i -> i.x == -1 || i.y == -1 || i.z == -1} == null
    }

    @Ignore
    def "scenario with item bearing capacity and stacking groups"() {
        def items = getHardScenario()
        Collections.shuffle(items, new Random(1234))
        GRASPModel model = new GRASPModel(
                items.toArray(new Item[0]),
                [Helper.getAddSpaceContainer2(4, 9, 3, 1000)] as core.base.container.Container[],
                new GRASPParameter(),
                Helper.getStatusManager()
        )

        when:
        service.execute(model)
        then:
        model.containers.length == 1
        items.find {i -> i.x == -1 || i.y == -1 || i.z == -1} == null
    }

    static List<Item> getHardScenario() {
        return [
                Helper.getItem(2, 2, 1, 3, 3, 1),
                Helper.getItem(2, 2, 1, 3, 3, 1),
                Helper.getItem(2, 2, 1, 1, 1, 1),
                Helper.getItem(2, 1, 1, 3, 3, 2),
                Helper.getItem(2, 1, 1, 3, 3, 2),
                Helper.getItem(2, 1, 1, 2, 2, 1),
                Helper.getItem(2, 1, 1, 2, 2, 2),
                Helper.getItem(2, 1, 1, 1, 1, 1),
                Helper.getItem(2, 3, 1, 3, 3, 2),
                Helper.getItem(2, 3, 1, 2, 2, 2),
                Helper.getItem(2, 3, 1, 1, 1, 2),
                Helper.getItem(2, 4, 1, 1, 1, 1),
                Helper.getItem(1, 4, 1, 1, 1, 2),

                Helper.getItem(2, 4, 2, 3, 3, 1),
                Helper.getItem(1, 4, 2, 3, 3, 1),
                Helper.getItem(2, 1, 2, 3, 3, 2),
                Helper.getItem(2, 1, 2, 2, 2, 2),
                Helper.getItem(2, 3, 2, 2, 2, 1),

                // Monster-Scenario
                // getItem(1, 4, 3, 3, 3, 2)
        ]

    }
}
