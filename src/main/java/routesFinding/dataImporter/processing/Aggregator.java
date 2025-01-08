package routesFinding.dataImporter.processing;

import core.base.container.SKU;
import routesFinding.dataImporter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aggregator {

    public List<OrderHeadDetail> mergeOrderHeadAndOrderDetail(List<OrderHeadData> orderHeadsList, List<OrderDetailData> orderDetailsList) {
        // Step 1: Join order_head and order_detail
        Map<String, List<OrderDetailData>> orderDetailDataHashMap = new HashMap<>();
        // 将 order_details 按照 orderID 进行分组
        for (OrderDetailData orderDetailData : orderDetailsList) {
            if (!orderDetailDataHashMap.containsKey(orderDetailData.getOrderID())) {
                orderDetailDataHashMap.put(orderDetailData.getOrderID(), new ArrayList<>());
            }
            orderDetailDataHashMap.get(orderDetailData.getOrderID()).add(orderDetailData);
        }
        // 将 order_head 和 order_detail 合并
        List<OrderHeadDetail> orderHeadDetails = new ArrayList<>();
        for (OrderHeadData orderHeadData : orderHeadsList) {
            if (!orderHeadData.getDestLocationID().equals("SGMW-1000")) {
                continue;
            }
            if (orderDetailDataHashMap.containsKey(orderHeadData.getOrderID())) {
                for (OrderDetailData orderDetailData1 : orderDetailDataHashMap.get(orderHeadData.getOrderID())) {
                    OrderHeadDetail headDetail = new OrderHeadDetail(orderHeadData, orderDetailData1);
                    orderHeadDetails.add(headDetail);
                }
            }
        }
        return orderHeadDetails;
    }

    public List<OrderHeadDetailSkuSupplier> aggregateCustomers(List<CustomerData> customerDataList, List<OrderHeadData> orderHeadsList, List<OrderDetailData> orderDetailsList, List<SKUpfepData> skuDataList) {
        // Step 1: Join order_head and order_detail
        List<OrderHeadDetail> orderHeadDetails = mergeOrderHeadAndOrderDetail(orderHeadsList,orderDetailsList);

        // Step 2: Join order_head_detail and sku
        List<OrderHeadDetailSku> orderHeadDetailSkus = new ArrayList<>();
        for (OrderHeadDetail headDetail : orderHeadDetails) {
            for (SKUpfepData sku : skuDataList) {
                if (headDetail.getSkuID().equals(sku.getSkuId())) {
                    OrderHeadDetailSku detailSku = new OrderHeadDetailSku(headDetail, sku);
                    orderHeadDetailSkus.add(detailSku);
                    break;
                }
            }
        }

        // Step 3: Join with customer data
        List<OrderHeadDetailSkuSupplier> orderHeadDetailSkuSuppliers = new ArrayList<>();
        for (OrderHeadDetailSku detailSku : orderHeadDetailSkus){
            for (CustomerData customerData : customerDataList){
                if (detailSku.getLocationID().equals(customerData.getLocationID())){
                    OrderHeadDetailSkuSupplier orderHeadDetailSkuSupplier = new OrderHeadDetailSkuSupplier(
                            detailSku.getOrderID(),
                            detailSku.getLocationID(),
                            detailSku.getSkuId(),
                            detailSku.getLength(),
                            detailSku.getWidth(),
                            detailSku.getHeight(),
                            detailSku.getSkuNum(),
                            detailSku.getSkuType(),
                            customerData.getLat(),
                            customerData.getLon(),
                            customerData.getDuration());
                    orderHeadDetailSkuSuppliers.add(orderHeadDetailSkuSupplier);
                }
            }
        }
        return orderHeadDetailSkuSuppliers;
    }

}