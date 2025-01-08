
# GRASP-3DBPP
GRASP-3DBPP is a solver for container loading problems(package,pallet,truck) in 3D with real world constraints

# Introduction

It supports:
* 装箱、装托、装车
* 单个容器或多个容器
* 6种放置状态

* Constraints:

  | Constraints         | Package | Pallet | Vehicle |
  |---------------------|---------|--------|---------|
  | _**Container-related**_   |         |        |         |
  | Weight limits       | ✔️      | ✔️     | ✔️      |
  | Weight distribution | ✔️      | ✔️     | ✔️      |
  | _**Item-related**_        |         |        |         |
  | Orientation         | ✔️      | ✔️     | ✔️      |
  | Stacking            | ✔️      | ✔️     | ✔️      |
  | Last in, first out  |         |        | ✔️      |
  | _**Cargo-related**_       |         |        |         |
  | Allocation          |         |        |         |
  | Positioning         | ✔️      | ✔️     | ✔️      |
  | _**Load-related**_        |         |        |         |
  | Stability           |         | ✔️     | ✔️      |

  * Max height of loading space (container,vehicle)
  * Max weight of loading space (container,pallet,vehicle)
  * Max bearing weight of each item (container,pallet,vehicle)
  * Collision constraint: space between items does not cross (container,pallet,vehicle)
  * Support constraint: the supporting surface between objects must be greater than a certain proportion (container,pallet,vehicle)
  * last in, first out condition (vehicle)
  * Unlimit height of loading space (pallet)
* consideration of stacking groups
* consideration of container types
* consideration of immersive depth during stacking
* consideration of permissible axle load (2 axles)

Optimization:
* Construction heuristic
* GRASP heuristic
  * Swap and relocate neighborhood search


# Usage
```
SolverInterface solver = SolverGenerate.getSolverByType("package");

solver.addPackage(410, 268, 235, 99999);
solver.addPackage(380, 260, 180, 99999);
solver.addPackage(248, 198, 150, 99999);
solver.addPackage(298, 208, 180, 99999);

solver.addItem(120,30,125,0,50);
solver.addItem(170,50,15,0, 5);
solver.addItem(150,135,25,0, 10);
solver.addItem(200,80,25,0, 20);
solver.addItem(160,40,20,0,10);

solver.optimizer();

try {
    solver.getReport();
} catch (IOException e) {
    throw new RuntimeException(e);
}

```

# 接口文档

### 调用参数

jar包调用示例如下：

```cmd
java - 
```

### 算法配置参数

算法配置通过config.yaml文件传入，放在输入数据文件夹中。

```yaml
## 优化目标，string, 取值范围(space:最大化装载率, cost:最小化箱型成本, container_number:箱型数量)
target:cost

##
```
### 输入数据格式


#### 箱型数据


#### 物品数据


### 输出数据格式

#### 装载物品结果

#### 未装载物品结果

### 部署说明

### 错误代码

# TODO List
* 扩展6种旋转方向 done
* 输出位置bug done
* 超过容器壁检查 done
* positionCandiate检查 done
* 选箱并没有选择装载率最大的箱子，优化目标需要重新考虑(多箱型排序) done
* 输出items按装载顺序 
* 物品重叠bug 待更多数据验证 
* 稳定性约束会造成多个items装不下，可视化没有出现接触面很低的情况 待更多数据验证
* 支撑面约束有bug 待验证
* 输出未打包items文件 done
* 旋转方式可选择（打包默认6种，装托和装车默认2种） done
* 装托的物品放置顺序，边界是否可超容器边缘参数设置
* 打包的6种旋转方式中优先选择底面积最大的然后按现有策略选择位置 done
* fastfixed目标函数中优化多箱子位置列表采用启发式策略代替取第一个 done
* 多箱型中，目标函数最小化箱子数量 done
* 
* SINGLE_CONTAINER_OPTIMIZER如果在construction阶段全部能装下，最终report未保存结果 done
* 多箱型优化，使用足够数量的箱子未全部装下物品

9.18-9.22
* construction阶段并行设计考虑不同物品摆放顺序，然后根据结果选择一个最优顺序 done
* 新增目标函数，考虑成本 done
* 支撑比例问题 done
* 冷运dfs baseline作为对比 

9.23-9.28
* 单元测 - 重叠约束、支撑比例约束 done
* 实验设计 - construction阶段四种顺序初始解对比，比对10组数据，解评估标准装多少，剩余多少 done
* 实验设计 - 多箱型目标函数效果 done

* construction初始解强化学习确定一个最优顺序
* 冷运dfs baseline作为对比 
