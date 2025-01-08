package routesFinding.dataImporter.processing;

import core.base.container.Vehicle;
import routesFinding.dataImporter.EquipmentData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class VehicleGenerator {
    private List<EquipmentData> equipmentDatas;

    public VehicleGenerator(List<EquipmentData> equipmentDatas){
        this.equipmentDatas = equipmentDatas;
    }

    public Vehicle getOneVehicle() {
        // 随机挑选一个车
        Random random = new Random();
        int vehicleNum = equipmentDatas.size();
        EquipmentData equipmentData = equipmentDatas.get(random.nextInt(vehicleNum));
        Vehicle vehicle = new Vehicle();
        vehicle.setType(equipmentData.getVehicleId());
        vehicle.setLength(equipmentData.getLength());
        vehicle.setWidth(equipmentData.getWidth());
        vehicle.setHeight(equipmentData.getHeight());
        return vehicle;
    }

    public Vehicle getMaxOneVehicle() {
        // 挑选最大的车
        EquipmentData equipmentData = findMaxVolumeEquipment();
        Vehicle vehicle = new Vehicle();
        vehicle.setType(equipmentData.getVehicleId());
        vehicle.setLength(equipmentData.getLength());
        vehicle.setWidth(equipmentData.getWidth());
        vehicle.setHeight(equipmentData.getHeight());
        return vehicle;
    }
    private EquipmentData findMaxVolumeEquipment() {
        if (equipmentDatas.isEmpty()) {
            return null; // 没有可用车辆
        }
        EquipmentData maxVolumeEquipment = equipmentDatas.get(0);
        for (EquipmentData equipmentData : equipmentDatas) {
            // 计算体积并比较
            BigDecimal containerLength = BigDecimal.valueOf(equipmentData.getLength());
            BigDecimal containerWidth = BigDecimal.valueOf(equipmentData.getWidth());
            BigDecimal containerHeight = BigDecimal.valueOf(equipmentData.getHeight());
            BigDecimal currentVolume = containerLength.multiply(containerWidth).multiply(containerHeight);
            BigDecimal maxContainerLength = BigDecimal.valueOf(maxVolumeEquipment.getLength());
            BigDecimal maxContainerWidth = BigDecimal.valueOf(maxVolumeEquipment.getWidth());
            BigDecimal maxContainerHeight = BigDecimal.valueOf(maxVolumeEquipment.getHeight());
            BigDecimal maxVolume = maxContainerLength.multiply(maxContainerWidth).multiply(maxContainerHeight);
            if (currentVolume.compareTo(maxVolume) >= 0) {
                maxVolumeEquipment = equipmentData;
            }
        }
        return maxVolumeEquipment;
    }

}
