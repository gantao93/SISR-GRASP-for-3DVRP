package core.base.fleximport;

import core.base.GRASPParameter;
import core.base.container.*;
import core.base.container.constraints.AxleLoadParameter;
import core.base.item.Position;
import core.base.item.PositionType;
import core.exception.Exception;
import core.exception.ExceptionType;
import core.opt.GRASPEnvType;
import core.base.container.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class ContainerData implements Serializable,Comparable<ContainerData> {

	private static final long serialVersionUID = -5241212560474818458L;
	private int width = Integer.MAX_VALUE;
	private int length = Integer.MAX_VALUE;
	private int height = Integer.MAX_VALUE;
	private float maxWeight = Float.MAX_VALUE;

	private String containerType = String.valueOf(GRASPEnvType.PACKAGE); // 修改于0627
//	public String containerType = "default_container_type";

	// Configuration of permissible axle load
	private float firstPermissibleAxleLoad = Float.MAX_VALUE;
	private float secondPermissibleAxleLoad = Float.MAX_VALUE;
	private float axleDistance = 0;

//	private List<Position> activePosition = new ArrayList<>();

	public int getVolume(){
		return length*width*height;
	}

	@Override
	public int compareTo(ContainerData o) {
		return this.getVolume() - o.getVolume(); // 只根据体积升序排序
	}

	/**
	 * @param width the width to set
	 */
	public final ContainerData setWidth(int width) {
		this.width = width;
		return this;
	}
	/**
	 * @param length the length to set
	 */
	public final ContainerData setLength(int length) {
		this.length = length;
		return this;
	}
	/**
	 * @param height the height to set
	 */
	public final ContainerData setHeight(int height) {
		this.height = height;
		return this;
	}
	/**
	 * @param maxWeight the maxWeight to set
	 */
	public final ContainerData setMaxWeight(float maxWeight) {
		this.maxWeight = maxWeight;
		return this;
	}
	/**
	 * @param containerType the containerType to set
	 */
	public final ContainerData setContainerType(String containerType) {
		this.containerType = containerType;
		return this;
	}

//	public final ContainerData setActivePosition(List<Position> activePosition){
//		this.activePosition = activePosition;
//		return this;
//	}

	public void setFirstPermissibleAxleLoad(float firstPermissibleAxleLoad) {
		this.firstPermissibleAxleLoad = firstPermissibleAxleLoad;
	}

	public void setSecondPermissibleAxleLoad(float secondPermissibleAxleLoad) {
		this.secondPermissibleAxleLoad = secondPermissibleAxleLoad;
	}

	public void setAxleDistance(float axleDistance) {
		this.axleDistance = axleDistance;
	}

	////////////////////////////////////////

	/**
	 * @return the containerType
	 */
	String getContainerType() {
		return containerType;
	}

	Container create(DataManager manager, GRASPParameter parameter, boolean isAddingAndRemovingItems) throws Exception {
		Class<? extends Container> correctContainerClass = (isAddingAndRemovingItems)
				? AddRemoveContainer.class
				: AddContainer.class;

		for (Constructor<?> constructor : correctContainerClass.getConstructors()) {
			try {
				ContainerParameter containerParameter = new DirectContainerParameter();
				containerParameter.add(ParameterType.GROUND_CONTACT_RULE, parameter.getGroundContactRule());
				containerParameter.add(ParameterType.LIFO_IMPORTANCE, 1f);
				containerParameter.add(ParameterType.AXLE_LOAD, new AxleLoadParameter(
						firstPermissibleAxleLoad, secondPermissibleAxleLoad, axleDistance
				));
				return (Container) constructor.newInstance(
						width,
						length,
						height,
						maxWeight,
						manager.getContainerTypeIdx(containerType),
//						activePosition,
						containerParameter
				);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
				throw new Exception(ExceptionType.ILLEGAL_STATE, e.getMessage(), e);
			}
		}
		throw new Exception(ExceptionType.ILLEGAL_STATE, "Could not find correct constructor for container");
	}
}
