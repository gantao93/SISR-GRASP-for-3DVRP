package core.opt.improvement


import spock.lang.Specification

class ItemOrderRandomSearchPackerTest extends Specification {

    def "test random search"() {
        core.GRASP xflp = build()
        xflp.setTypeOfOptimization(core.opt.GRASPOptType.FAST_FIXED_CONTAINER_PACKER)

        core.GRASP xflp2 = build()
        xflp2.setTypeOfOptimization(core.opt.GRASPOptType.BEST_FIXED_CONTAINER_PACKER)

        when:
        xflp.executeLoadPlanning()
        def rep = xflp.getReport()

        xflp2.executeLoadPlanning()
        def rep2 = xflp2.getReport()

        then:
        rep.getUnplannedPackages().size() > 0
        rep2.getUnplannedPackages().size() == 0
    }

    private core.GRASP build() {
        def xflp = new core.GRASP()
        xflp.addContainer().setContainerType("C1").setWidth(3).setLength(3).setHeight(3)
        xflp.addItem().setExternID("P1").setWidth(1).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P2").setWidth(1).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P3").setWidth(1).setLength(1).setHeight(1).setWeight(1)

        xflp.addItem().setExternID("P4").setWidth(2).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P5").setWidth(2).setLength(1).setHeight(1).setWeight(1)

        xflp.addItem().setExternID("P6").setWidth(2).setLength(2).setHeight(1).setWeight(1)

        xflp.addItem().setExternID("P7").setWidth(2).setLength(1).setHeight(2).setWeight(1)
        xflp.addItem().setExternID("P8").setWidth(3).setLength(1).setHeight(2).setWeight(1)

        xflp.addItem().setExternID("P9").setWidth(3).setLength(1).setHeight(1).setWeight(1)
        xflp.addItem().setExternID("P0").setWidth(3).setLength(1).setHeight(1).setWeight(1)

        return xflp
    }
}
