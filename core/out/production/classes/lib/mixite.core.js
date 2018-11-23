if (typeof kotlin === 'undefined') {
  throw new Error("Error loading module 'mixite.core'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'mixite.core'.");
}
this['mixite.core'] = function (_, Kotlin) {
  'use strict';
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var UnsupportedOperationException_init = Kotlin.kotlin.UnsupportedOperationException_init_pdl1vj$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var toString = Kotlin.toString;
  var toInt = Kotlin.kotlin.text.toInt_pdl1vz$;
  var IllegalArgumentException = Kotlin.kotlin.IllegalArgumentException;
  var Exception = Kotlin.kotlin.Exception;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init_pdl1vj$;
  var kotlin_js_internal_DoubleCompanionObject = Kotlin.kotlin.js.internal.DoubleCompanionObject;
  var ensureNotNull = Kotlin.ensureNotNull;
  var LinkedHashMap_init = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var math = Kotlin.kotlin.math;
  var throwCCE = Kotlin.throwCCE;
  var abs = Kotlin.kotlin.math.abs_za3lpa$;
  var HashSet_init = Kotlin.kotlin.collections.HashSet_init_287e2$;
  var equals = Kotlin.equals;
  var round = Kotlin.kotlin.math.round_14dthe$;
  var numberToInt = Kotlin.numberToInt;
  var Iterator = Kotlin.kotlin.collections.Iterator;
  var Iterable = Kotlin.kotlin.collections.Iterable;
  var NoSuchElementException = Kotlin.kotlin.NoSuchElementException;
  var hashCode = Kotlin.hashCode;
  HexagonOrientation.prototype = Object.create(Enum.prototype);
  HexagonOrientation.prototype.constructor = HexagonOrientation;
  HexagonalGridLayout.prototype = Object.create(Enum.prototype);
  HexagonalGridLayout.prototype.constructor = HexagonalGridLayout;
  RotationDirection.prototype = Object.create(Enum.prototype);
  RotationDirection.prototype.constructor = RotationDirection;
  HexagonalGridLayoutStrategy.prototype = Object.create(GridLayoutStrategy.prototype);
  HexagonalGridLayoutStrategy.prototype.constructor = HexagonalGridLayoutStrategy;
  RectangularGridLayoutStrategy.prototype = Object.create(GridLayoutStrategy.prototype);
  RectangularGridLayoutStrategy.prototype.constructor = RectangularGridLayoutStrategy;
  TrapezoidGridLayoutStrategy.prototype = Object.create(GridLayoutStrategy.prototype);
  TrapezoidGridLayoutStrategy.prototype.constructor = TrapezoidGridLayoutStrategy;
  TriangularGridLayoutStrategy.prototype = Object.create(GridLayoutStrategy.prototype);
  TriangularGridLayoutStrategy.prototype.constructor = TriangularGridLayoutStrategy;
  function CoordinateConverter() {
    CoordinateConverter$Companion_getInstance();
    throw UnsupportedOperationException_init('This utility class is not meant to be instantiated.');
  }
  function CoordinateConverter$Companion() {
    CoordinateConverter$Companion_instance = this;
  }
  CoordinateConverter$Companion.prototype.convertOffsetCoordinatesToCubeX_edbmww$ = function (offsetX, offsetY, orientation) {
    return HexagonOrientation$FLAT_TOP_getInstance().equals(orientation) ? offsetX : offsetX - (offsetY / 2 | 0) | 0;
  };
  CoordinateConverter$Companion.prototype.convertOffsetCoordinatesToCubeZ_edbmww$ = function (offsetX, offsetY, orientation) {
    return HexagonOrientation$FLAT_TOP_getInstance().equals(orientation) ? offsetY - (offsetX / 2 | 0) | 0 : offsetY;
  };
  CoordinateConverter$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var CoordinateConverter$Companion_instance = null;
  function CoordinateConverter$Companion_getInstance() {
    if (CoordinateConverter$Companion_instance === null) {
      new CoordinateConverter$Companion();
    }
    return CoordinateConverter$Companion_instance;
  }
  CoordinateConverter.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CoordinateConverter',
    interfaces: []
  };
  function CubeCoordinate(gridX, gridZ) {
    CubeCoordinate$Companion_getInstance();
    this.gridX = gridX;
    this.gridZ = gridZ;
  }
  Object.defineProperty(CubeCoordinate.prototype, 'gridY', {
    get: function () {
      return -(this.gridX + this.gridZ | 0) | 0;
    }
  });
  CubeCoordinate.prototype.toAxialKey = function () {
    return this.gridX.toString() + CubeCoordinate$Companion_getInstance().SEP + toString(this.gridZ);
  };
  function CubeCoordinate$Companion() {
    CubeCoordinate$Companion_instance = this;
    this.SEP = ',';
  }
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  var take = Kotlin.kotlin.collections.take_ba2ldo$;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  CubeCoordinate$Companion.prototype.fromAxialKey_61zpoe$ = function (axialKey) {
    var result;
    try {
      var $receiver = Regex_init(this.SEP).split_905azu$(axialKey, 0);
      var dropLastWhile$result;
      dropLastWhile$break: do {
        if (!$receiver.isEmpty()) {
          var iterator = $receiver.listIterator_za3lpa$($receiver.size);
          while (iterator.hasPrevious()) {
            if (!(iterator.previous().length === 0)) {
              dropLastWhile$result = take($receiver, iterator.nextIndex() + 1 | 0);
              break dropLastWhile$break;
            }
          }
        }
        dropLastWhile$result = emptyList();
      }
       while (false);
      var coords = copyToArray(dropLastWhile$result);
      result = this.fromCoordinates_vux9f0$(toInt(coords[0]), toInt(coords[1]));
    }
     catch (e) {
      if (Kotlin.isType(e, Exception)) {
        throw new IllegalArgumentException('Failed to create CubeCoordinate from key: ' + axialKey, e);
      }
       else
        throw e;
    }
    return result;
  };
  CubeCoordinate$Companion.prototype.fromCoordinates_vux9f0$ = function (gridX, gridZ) {
    return new CubeCoordinate(gridX, gridZ);
  };
  CubeCoordinate$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var CubeCoordinate$Companion_instance = null;
  function CubeCoordinate$Companion_getInstance() {
    if (CubeCoordinate$Companion_instance === null) {
      new CubeCoordinate$Companion();
    }
    return CubeCoordinate$Companion_instance;
  }
  CubeCoordinate.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'CubeCoordinate',
    interfaces: []
  };
  CubeCoordinate.prototype.component1 = function () {
    return this.gridX;
  };
  CubeCoordinate.prototype.component2 = function () {
    return this.gridZ;
  };
  CubeCoordinate.prototype.copy_vux9f0$ = function (gridX, gridZ) {
    return new CubeCoordinate(gridX === void 0 ? this.gridX : gridX, gridZ === void 0 ? this.gridZ : gridZ);
  };
  CubeCoordinate.prototype.toString = function () {
    return 'CubeCoordinate(gridX=' + Kotlin.toString(this.gridX) + (', gridZ=' + Kotlin.toString(this.gridZ)) + ')';
  };
  CubeCoordinate.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.gridX) | 0;
    result = result * 31 + Kotlin.hashCode(this.gridZ) | 0;
    return result;
  };
  CubeCoordinate.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.gridX, other.gridX) && Kotlin.equals(this.gridZ, other.gridZ)))));
  };
  function Hexagon() {
  }
  Hexagon.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Hexagon',
    interfaces: []
  };
  function HexagonOrientation(name, ordinal, coordinateOffset) {
    Enum.call(this);
    this.coordinateOffset = coordinateOffset;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function HexagonOrientation_initFields() {
    HexagonOrientation_initFields = function () {
    };
    HexagonOrientation$POINTY_TOP_instance = new HexagonOrientation('POINTY_TOP', 0, 0.5);
    HexagonOrientation$FLAT_TOP_instance = new HexagonOrientation('FLAT_TOP', 1, 0.0);
  }
  var HexagonOrientation$POINTY_TOP_instance;
  function HexagonOrientation$POINTY_TOP_getInstance() {
    HexagonOrientation_initFields();
    return HexagonOrientation$POINTY_TOP_instance;
  }
  var HexagonOrientation$FLAT_TOP_instance;
  function HexagonOrientation$FLAT_TOP_getInstance() {
    HexagonOrientation_initFields();
    return HexagonOrientation$FLAT_TOP_instance;
  }
  HexagonOrientation.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexagonOrientation',
    interfaces: [Enum]
  };
  function HexagonOrientation$values() {
    return [HexagonOrientation$POINTY_TOP_getInstance(), HexagonOrientation$FLAT_TOP_getInstance()];
  }
  HexagonOrientation.values = HexagonOrientation$values;
  function HexagonOrientation$valueOf(name) {
    switch (name) {
      case 'POINTY_TOP':
        return HexagonOrientation$POINTY_TOP_getInstance();
      case 'FLAT_TOP':
        return HexagonOrientation$FLAT_TOP_getInstance();
      default:throwISE('No enum constant org.hexworks.mixite.core.api.HexagonOrientation.' + name);
    }
  }
  HexagonOrientation.valueOf_61zpoe$ = HexagonOrientation$valueOf;
  function HexagonalGrid() {
  }
  HexagonalGrid.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HexagonalGrid',
    interfaces: []
  };
  function HexagonalGridBuilder() {
    this.gridWidth_0 = 0;
    this.gridHeight_0 = 0;
    this.radius_0 = 0;
    this.hexagonDataStorage_0 = new DefaultHexagonDataStorage();
    this.orientation_0 = HexagonOrientation$POINTY_TOP_getInstance();
    this.gridLayout_0 = HexagonalGridLayout$RECTANGULAR_getInstance();
  }
  Object.defineProperty(HexagonalGridBuilder.prototype, 'gridLayoutStrategy', {
    get: function () {
      return this.gridLayout_0.gridLayoutStrategy;
    }
  });
  Object.defineProperty(HexagonalGridBuilder.prototype, 'gridData', {
    get: function () {
      if (this.radius_0 === 0.0 || this.gridWidth_0 === 0 || this.gridHeight_0 === 0) {
        throw IllegalStateException_init('Not all necessary fields are initialized!');
      }
      return new GridData(this.orientation_0, this.gridLayout_0, this.radius_0, this.gridWidth_0, this.gridHeight_0);
    }
  });
  HexagonalGridBuilder.prototype.build = function () {
    this.checkParameters_0();
    return new HexagonalGridImpl(this);
  };
  HexagonalGridBuilder.prototype.buildCalculatorFor_ifjfoe$ = function (hexagonalGrid) {
    return new HexagonalGridCalculatorImpl(hexagonalGrid);
  };
  HexagonalGridBuilder.prototype.getRadius = function () {
    return this.radius_0;
  };
  HexagonalGridBuilder.prototype.setRadius_14dthe$ = function (radius) {
    this.radius_0 = radius;
    return this;
  };
  HexagonalGridBuilder.prototype.getGridWidth = function () {
    return this.gridWidth_0;
  };
  HexagonalGridBuilder.prototype.setGridWidth_za3lpa$ = function (gridWidth) {
    this.gridWidth_0 = gridWidth;
    return this;
  };
  HexagonalGridBuilder.prototype.getGridHeight = function () {
    return this.gridHeight_0;
  };
  HexagonalGridBuilder.prototype.setGridHeight_za3lpa$ = function (gridHeight) {
    this.gridHeight_0 = gridHeight;
    return this;
  };
  HexagonalGridBuilder.prototype.getOrientation = function () {
    return this.orientation_0;
  };
  HexagonalGridBuilder.prototype.setOrientation_ar1dz4$ = function (orientation) {
    this.orientation_0 = orientation;
    return this;
  };
  HexagonalGridBuilder.prototype.getHexagonDataStorage = function () {
    return this.hexagonDataStorage_0;
  };
  HexagonalGridBuilder.prototype.setGridLayout_osuq8b$ = function (gridLayout) {
    this.gridLayout_0 = gridLayout;
    return this;
  };
  HexagonalGridBuilder.prototype.checkParameters_0 = function () {
    if (this.radius_0 <= 0) {
      throw IllegalStateException_init('Radius must be greater than 0.');
    }
    if (!this.gridLayout_0.checkParameters_6xvm5r$(this.gridHeight_0, this.gridWidth_0)) {
      throw IllegalStateException_init('Width: ' + toString(this.gridWidth_0) + ' and height: ' + toString(this.gridHeight_0) + ' is not valid for: ' + this.gridLayout_0.name + ' layout.');
    }
  };
  HexagonalGridBuilder.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexagonalGridBuilder',
    interfaces: []
  };
  function HexagonalGridCalculator() {
  }
  HexagonalGridCalculator.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HexagonalGridCalculator',
    interfaces: []
  };
  function HexagonalGridLayout(name, ordinal, gridLayoutStrategy) {
    Enum.call(this);
    this.gridLayoutStrategy = gridLayoutStrategy;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function HexagonalGridLayout_initFields() {
    HexagonalGridLayout_initFields = function () {
    };
    HexagonalGridLayout$RECTANGULAR_instance = new HexagonalGridLayout('RECTANGULAR', 0, new RectangularGridLayoutStrategy());
    HexagonalGridLayout$HEXAGONAL_instance = new HexagonalGridLayout('HEXAGONAL', 1, new HexagonalGridLayoutStrategy());
    HexagonalGridLayout$TRIANGULAR_instance = new HexagonalGridLayout('TRIANGULAR', 2, new TriangularGridLayoutStrategy());
    HexagonalGridLayout$TRAPEZOID_instance = new HexagonalGridLayout('TRAPEZOID', 3, new TrapezoidGridLayoutStrategy());
  }
  var HexagonalGridLayout$RECTANGULAR_instance;
  function HexagonalGridLayout$RECTANGULAR_getInstance() {
    HexagonalGridLayout_initFields();
    return HexagonalGridLayout$RECTANGULAR_instance;
  }
  var HexagonalGridLayout$HEXAGONAL_instance;
  function HexagonalGridLayout$HEXAGONAL_getInstance() {
    HexagonalGridLayout_initFields();
    return HexagonalGridLayout$HEXAGONAL_instance;
  }
  var HexagonalGridLayout$TRIANGULAR_instance;
  function HexagonalGridLayout$TRIANGULAR_getInstance() {
    HexagonalGridLayout_initFields();
    return HexagonalGridLayout$TRIANGULAR_instance;
  }
  var HexagonalGridLayout$TRAPEZOID_instance;
  function HexagonalGridLayout$TRAPEZOID_getInstance() {
    HexagonalGridLayout_initFields();
    return HexagonalGridLayout$TRAPEZOID_instance;
  }
  HexagonalGridLayout.prototype.checkParameters_6xvm5r$ = function (gridHeight, gridWidth) {
    return this.gridLayoutStrategy.checkParameters_vux9f0$(gridHeight, gridWidth);
  };
  HexagonalGridLayout.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexagonalGridLayout',
    interfaces: [Enum]
  };
  function HexagonalGridLayout$values() {
    return [HexagonalGridLayout$RECTANGULAR_getInstance(), HexagonalGridLayout$HEXAGONAL_getInstance(), HexagonalGridLayout$TRIANGULAR_getInstance(), HexagonalGridLayout$TRAPEZOID_getInstance()];
  }
  HexagonalGridLayout.values = HexagonalGridLayout$values;
  function HexagonalGridLayout$valueOf(name) {
    switch (name) {
      case 'RECTANGULAR':
        return HexagonalGridLayout$RECTANGULAR_getInstance();
      case 'HEXAGONAL':
        return HexagonalGridLayout$HEXAGONAL_getInstance();
      case 'TRIANGULAR':
        return HexagonalGridLayout$TRIANGULAR_getInstance();
      case 'TRAPEZOID':
        return HexagonalGridLayout$TRAPEZOID_getInstance();
      default:throwISE('No enum constant org.hexworks.mixite.core.api.HexagonalGridLayout.' + name);
    }
  }
  HexagonalGridLayout.valueOf_61zpoe$ = HexagonalGridLayout$valueOf;
  function Point(coordinateX, coordinateY) {
    Point$Companion_getInstance();
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
  }
  var Math_0 = Math;
  Point.prototype.distanceFrom_ievl7k$ = function (point) {
    var x = (this.coordinateX - point.coordinateX) * (this.coordinateX - point.coordinateX) + (this.coordinateY - point.coordinateY) * (this.coordinateY - point.coordinateY);
    return Math_0.sqrt(x);
  };
  function Point$Companion() {
    Point$Companion_instance = this;
  }
  Point$Companion.prototype.fromPosition_lu1900$ = function (coordinateX, coordinateY) {
    return new Point(coordinateX, coordinateY);
  };
  Point$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Point$Companion_instance = null;
  function Point$Companion_getInstance() {
    if (Point$Companion_instance === null) {
      new Point$Companion();
    }
    return Point$Companion_instance;
  }
  Point.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Point',
    interfaces: []
  };
  Point.prototype.component1 = function () {
    return this.coordinateX;
  };
  Point.prototype.component2 = function () {
    return this.coordinateY;
  };
  Point.prototype.copy_lu1900$ = function (coordinateX, coordinateY) {
    return new Point(coordinateX === void 0 ? this.coordinateX : coordinateX, coordinateY === void 0 ? this.coordinateY : coordinateY);
  };
  Point.prototype.toString = function () {
    return 'Point(coordinateX=' + Kotlin.toString(this.coordinateX) + (', coordinateY=' + Kotlin.toString(this.coordinateY)) + ')';
  };
  Point.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.coordinateX) | 0;
    result = result * 31 + Kotlin.hashCode(this.coordinateY) | 0;
    return result;
  };
  Point.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.coordinateX, other.coordinateX) && Kotlin.equals(this.coordinateY, other.coordinateY)))));
  };
  function Rectangle() {
    this.x = 0.0;
    this.y = 0.0;
    this.width = 0.0;
    this.height = 0.0;
  }
  Object.defineProperty(Rectangle.prototype, 'aspectRatio', {
    get: function () {
      return this.height === 0.0 ? kotlin_js_internal_DoubleCompanionObject.NaN : this.width / this.height;
    }
  });
  Rectangle.prototype.withRectangle_6y0v78$ = function (x, y, width, height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    return this;
  };
  Rectangle.prototype.withX_14dthe$ = function (x) {
    this.x = x;
    return this;
  };
  Rectangle.prototype.withY_14dthe$ = function (y) {
    this.y = y;
    return this;
  };
  Rectangle.prototype.withWidth_14dthe$ = function (width) {
    this.width = width;
    return this;
  };
  Rectangle.prototype.withHeight_14dthe$ = function (height) {
    this.height = height;
    return this;
  };
  Rectangle.prototype.withPosition_lu1900$ = function (x, y) {
    this.x = x;
    this.y = y;
    return this;
  };
  Rectangle.prototype.withSize_lu1900$ = function (width, height) {
    this.width = width;
    this.height = height;
    return this;
  };
  Rectangle.prototype.withSize_14dthe$ = function (sizeXY) {
    this.width = sizeXY;
    this.height = sizeXY;
    return this;
  };
  Rectangle.prototype.withRectangle_fvnklb$ = function (rect) {
    this.x = rect.x;
    this.y = rect.y;
    this.width = rect.width;
    this.height = rect.height;
    return this;
  };
  Rectangle.prototype.contains_lu1900$ = function (x, y) {
    return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
  };
  Rectangle.prototype.contains_fvnklb$ = function (rectangle) {
    var xMin = rectangle.x;
    var xMax = xMin + rectangle.width;
    var yMin = rectangle.y;
    var yMax = yMin + rectangle.height;
    return xMin > this.x && xMin < this.x + this.width && xMax > this.x && xMax < this.x + this.width && yMin > this.y && yMin < this.y + this.height && yMax > this.y && yMax < this.y + this.height;
  };
  Rectangle.prototype.overlaps_fvnklb$ = function (r) {
    return this.x < r.x + r.width && this.x + this.width > r.x && this.y < r.y + r.height && this.y + this.height > r.y;
  };
  Rectangle.prototype.withCenter_lu1900$ = function (x, y) {
    this.withPosition_lu1900$(x - this.width / 2, y - this.height / 2);
    return this;
  };
  Rectangle.prototype.toString = function () {
    return '[' + this.x + ',' + this.y + ',' + this.width + ',' + this.height + ']';
  };
  Rectangle.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Rectangle',
    interfaces: []
  };
  function Rectangle_init(x, y, width, height, $this) {
    $this = $this || Object.create(Rectangle.prototype);
    Rectangle.call($this);
    $this.x = x;
    $this.y = y;
    $this.width = width;
    $this.height = height;
    return $this;
  }
  function Rectangle_init_0(rect, $this) {
    $this = $this || Object.create(Rectangle.prototype);
    Rectangle.call($this);
    $this.x = rect.x;
    $this.y = rect.y;
    $this.width = rect.width;
    $this.height = rect.height;
    return $this;
  }
  function RotationDirection(name, ordinal, rotationCalculator) {
    Enum.call(this);
    this.rotationCalculator_gylexp$_0 = rotationCalculator;
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function RotationDirection_initFields() {
    RotationDirection_initFields = function () {
    };
    RotationDirection$RIGHT_instance = new RotationDirection('RIGHT', 0, new RotationDirection$RotationDirection$RIGHT_init$ObjectLiteral());
    RotationDirection$LEFT_instance = new RotationDirection('LEFT', 1, new RotationDirection$RotationDirection$LEFT_init$ObjectLiteral());
  }
  function RotationDirection$RotationDirection$RIGHT_init$ObjectLiteral() {
  }
  RotationDirection$RotationDirection$RIGHT_init$ObjectLiteral.prototype.calculate_eo2iv1$ = function (coord) {
    return CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(-coord.gridZ | 0, -coord.gridY | 0);
  };
  RotationDirection$RotationDirection$RIGHT_init$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [RotationDirection$RotationCalculator]
  };
  var RotationDirection$RIGHT_instance;
  function RotationDirection$RIGHT_getInstance() {
    RotationDirection_initFields();
    return RotationDirection$RIGHT_instance;
  }
  function RotationDirection$RotationDirection$LEFT_init$ObjectLiteral() {
  }
  RotationDirection$RotationDirection$LEFT_init$ObjectLiteral.prototype.calculate_eo2iv1$ = function (coord) {
    return CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(-coord.gridY | 0, -coord.gridX | 0);
  };
  RotationDirection$RotationDirection$LEFT_init$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [RotationDirection$RotationCalculator]
  };
  var RotationDirection$LEFT_instance;
  function RotationDirection$LEFT_getInstance() {
    RotationDirection_initFields();
    return RotationDirection$LEFT_instance;
  }
  RotationDirection.prototype.calculateRotation_eo2iv1$ = function (coord) {
    return this.rotationCalculator_gylexp$_0.calculate_eo2iv1$(coord);
  };
  function RotationDirection$RotationCalculator() {
  }
  RotationDirection$RotationCalculator.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'RotationCalculator',
    interfaces: []
  };
  RotationDirection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RotationDirection',
    interfaces: [Enum]
  };
  function RotationDirection$values() {
    return [RotationDirection$RIGHT_getInstance(), RotationDirection$LEFT_getInstance()];
  }
  RotationDirection.values = RotationDirection$values;
  function RotationDirection$valueOf(name) {
    switch (name) {
      case 'RIGHT':
        return RotationDirection$RIGHT_getInstance();
      case 'LEFT':
        return RotationDirection$LEFT_getInstance();
      default:throwISE('No enum constant org.hexworks.mixite.core.api.RotationDirection.' + name);
    }
  }
  RotationDirection.valueOf_61zpoe$ = RotationDirection$valueOf;
  function HexagonDataStorage() {
  }
  HexagonDataStorage.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'HexagonDataStorage',
    interfaces: []
  };
  function SatelliteData() {
  }
  SatelliteData.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'SatelliteData',
    interfaces: []
  };
  function DefaultHexagonDataStorage() {
    this.storage_0 = LinkedHashMap_init();
  }
  Object.defineProperty(DefaultHexagonDataStorage.prototype, 'coordinates', {
    get: function () {
      return this.storage_0.keys;
    }
  });
  DefaultHexagonDataStorage.prototype.addCoordinate_eo2iv1$ = function (cubeCoordinate) {
    var $receiver = this.storage_0;
    var value = Maybe$Companion_getInstance().empty_287e2$();
    $receiver.put_xwzc9p$(cubeCoordinate, value);
  };
  DefaultHexagonDataStorage.prototype.addCoordinate_5mr61k$ = function (cubeCoordinate, satelliteData) {
    var previous = this.storage_0.put_xwzc9p$(cubeCoordinate, Maybe$Companion_getInstance().of_mh5how$(satelliteData));
    return previous != null;
  };
  DefaultHexagonDataStorage.prototype.getSatelliteDataBy_eo2iv1$ = function (cubeCoordinate) {
    return this.storage_0.containsKey_11rb$(cubeCoordinate) ? ensureNotNull(this.storage_0.get_11rb$(cubeCoordinate)) : Maybe$Companion_getInstance().empty_287e2$();
  };
  DefaultHexagonDataStorage.prototype.containsCoordinate_eo2iv1$ = function (cubeCoordinate) {
    return this.storage_0.containsKey_11rb$(cubeCoordinate);
  };
  DefaultHexagonDataStorage.prototype.hasDataFor_eo2iv1$ = function (cubeCoordinate) {
    return this.storage_0.containsKey_11rb$(cubeCoordinate) && ensureNotNull(this.storage_0.get_11rb$(cubeCoordinate)).isPresent;
  };
  DefaultHexagonDataStorage.prototype.clearDataFor_eo2iv1$ = function (cubeCoordinate) {
    var result = false;
    if (this.hasDataFor_eo2iv1$(cubeCoordinate)) {
      result = true;
    }
    var $receiver = this.storage_0;
    var value = Maybe$Companion_getInstance().empty_287e2$();
    $receiver.put_xwzc9p$(cubeCoordinate, value);
    return result;
  };
  DefaultHexagonDataStorage.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'DefaultHexagonDataStorage',
    interfaces: [HexagonDataStorage]
  };
  function DefaultSatelliteData(passable, opaque, movementCost) {
    if (passable === void 0)
      passable = false;
    if (opaque === void 0)
      opaque = false;
    if (movementCost === void 0)
      movementCost = 0;
    this.passable_wbsa33$_0 = passable;
    this.opaque_cgtt6t$_0 = opaque;
    this.movementCost_wlmdr4$_0 = movementCost;
  }
  Object.defineProperty(DefaultSatelliteData.prototype, 'passable', {
    get: function () {
      return this.passable_wbsa33$_0;
    },
    set: function (passable) {
      this.passable_wbsa33$_0 = passable;
    }
  });
  Object.defineProperty(DefaultSatelliteData.prototype, 'opaque', {
    get: function () {
      return this.opaque_cgtt6t$_0;
    },
    set: function (opaque) {
      this.opaque_cgtt6t$_0 = opaque;
    }
  });
  Object.defineProperty(DefaultSatelliteData.prototype, 'movementCost', {
    get: function () {
      return this.movementCost_wlmdr4$_0;
    },
    set: function (movementCost) {
      this.movementCost_wlmdr4$_0 = movementCost;
    }
  });
  DefaultSatelliteData.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'DefaultSatelliteData',
    interfaces: [SatelliteData]
  };
  function GridData(orientation, gridLayout, radius, gridWidth, gridHeight) {
    this.orientation = orientation;
    this.gridLayout = gridLayout;
    this.radius = radius;
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
    this.hexagonHeight = 0;
    this.hexagonWidth = 0;
    this.hexagonHeight = HexagonOrientation$FLAT_TOP_getInstance() === this.orientation ? this.calculateHeight_0(this.radius) : this.calculateWidth_0(this.radius);
    this.hexagonWidth = HexagonOrientation$FLAT_TOP_getInstance() === this.orientation ? this.calculateWidth_0(this.radius) : this.calculateHeight_0(this.radius);
  }
  GridData.prototype.calculateHeight_0 = function (radius) {
    return Math_0.sqrt(3.0) * radius;
  };
  GridData.prototype.calculateWidth_0 = function (radius) {
    return radius * 3 / 2;
  };
  GridData.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GridData',
    interfaces: []
  };
  function HexagonImpl(sharedData, cubeCoordinate, hexagonDataStorage) {
    this.sharedData_0 = sharedData;
    this.cubeCoordinate_oqcuca$_0 = cubeCoordinate;
    this.hexagonDataStorage_0 = hexagonDataStorage;
    this.vertices_ik0egq$_0 = null;
    this.points_p1wd74$_0 = null;
    this.externalBoundingBox_pl422j$_0 = null;
    this.internalBoundingBox_fzxfg7$_0 = null;
    var tmp$;
    this.points_p1wd74$_0 = this.calculatePoints_0();
    var x1 = this.points.get_za3lpa$(3).coordinateX;
    var y1 = this.points.get_za3lpa$(2).coordinateY;
    var x2 = this.points.get_za3lpa$(0).coordinateX;
    var y2 = this.points.get_za3lpa$(5).coordinateY;
    this.externalBoundingBox_pl422j$_0 = Rectangle_init(x1, y1, x2 - x1, y2 - y1);
    this.internalBoundingBox_fzxfg7$_0 = Rectangle_init(this.centerX - 1.25 * this.sharedData_0.radius / 2, this.centerY - 1.25 * this.sharedData_0.radius / 2, 1.25 * this.sharedData_0.radius, 1.25 * this.sharedData_0.radius);
    this.vertices_ik0egq$_0 = ArrayList_init();
    tmp$ = this.points.iterator();
    while (tmp$.hasNext()) {
      var point = tmp$.next();
      this.vertices.add_11rb$(point.coordinateX);
      this.vertices.add_11rb$(point.coordinateY);
    }
  }
  Object.defineProperty(HexagonImpl.prototype, 'cubeCoordinate', {
    get: function () {
      return this.cubeCoordinate_oqcuca$_0;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'vertices', {
    get: function () {
      return this.vertices_ik0egq$_0;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'points', {
    get: function () {
      return this.points_p1wd74$_0;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'externalBoundingBox', {
    get: function () {
      return this.externalBoundingBox_pl422j$_0;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'internalBoundingBox', {
    get: function () {
      return this.internalBoundingBox_fzxfg7$_0;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'id', {
    get: function () {
      return this.cubeCoordinate.toAxialKey();
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'gridX', {
    get: function () {
      return this.cubeCoordinate.gridX;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'gridY', {
    get: function () {
      return this.cubeCoordinate.gridY;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'gridZ', {
    get: function () {
      return this.cubeCoordinate.gridZ;
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'centerX', {
    get: function () {
      if (HexagonOrientation$FLAT_TOP_getInstance().equals(this.sharedData_0.orientation)) {
        return this.cubeCoordinate.gridX * this.sharedData_0.hexagonWidth + this.sharedData_0.radius;
      }
       else {
        return this.cubeCoordinate.gridX * this.sharedData_0.hexagonWidth + this.cubeCoordinate.gridZ * this.sharedData_0.hexagonWidth / 2 + this.sharedData_0.hexagonWidth / 2;
      }
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'centerY', {
    get: function () {
      if (HexagonOrientation$FLAT_TOP_getInstance().equals(this.sharedData_0.orientation)) {
        return this.cubeCoordinate.gridZ * this.sharedData_0.hexagonHeight + this.cubeCoordinate.gridX * this.sharedData_0.hexagonHeight / 2 + this.sharedData_0.hexagonHeight / 2;
      }
       else {
        return this.cubeCoordinate.gridZ * this.sharedData_0.hexagonHeight + this.sharedData_0.radius;
      }
    }
  });
  Object.defineProperty(HexagonImpl.prototype, 'satelliteData', {
    get: function () {
      return this.hexagonDataStorage_0.getSatelliteDataBy_eo2iv1$(this.cubeCoordinate);
    }
  });
  HexagonImpl.prototype.calculatePoints_0 = function () {
    var points = ArrayList_init();
    for (var i = 0; i <= 5; i++) {
      var angle = 2 * math.PI / 6 * (i + this.sharedData_0.orientation.coordinateOffset);
      var x = this.centerX + this.sharedData_0.radius * Math_0.cos(angle);
      var y = this.centerY + this.sharedData_0.radius * Math_0.sin(angle);
      points.add_11rb$(Point$Companion_getInstance().fromPosition_lu1900$(x, y));
    }
    return points;
  };
  HexagonImpl.prototype.setSatelliteData_wcyakp$ = function (data) {
    this.hexagonDataStorage_0.addCoordinate_5mr61k$(this.cubeCoordinate, data);
  };
  HexagonImpl.prototype.clearSatelliteData = function () {
    this.hexagonDataStorage_0.clearDataFor_eo2iv1$(this.cubeCoordinate);
  };
  HexagonImpl.prototype.equals = function (other) {
    var tmp$, tmp$_0, tmp$_1;
    if (this === other)
      return true;
    if (other == null || !((tmp$ = Kotlin.getKClassFromExpression(this)) != null ? tmp$.equals(Kotlin.getKClassFromExpression(other)) : null))
      return false;
    Kotlin.isType(tmp$_0 = other, HexagonImpl) ? tmp$_0 : throwCCE();
    if (!((tmp$_1 = this.cubeCoordinate) != null ? tmp$_1.equals(other.cubeCoordinate) : null))
      return false;
    return true;
  };
  HexagonImpl.prototype.hashCode = function () {
    return this.cubeCoordinate.hashCode();
  };
  HexagonImpl.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexagonImpl',
    interfaces: [Hexagon]
  };
  function HexagonalGridCalculatorImpl(hexagonalGrid) {
    this.hexagonalGrid_0 = hexagonalGrid;
  }
  HexagonalGridCalculatorImpl.prototype.calculateDistanceBetween_2pi2gm$ = function (hex0, hex1) {
    var absX = abs(hex0.gridX - hex1.gridX | 0);
    var absY = abs(hex0.gridY - hex1.gridY | 0);
    var absZ = abs(hex0.gridZ - hex1.gridZ | 0);
    var a = Math_0.max(absX, absY);
    return Math_0.max(a, absZ);
  };
  HexagonalGridCalculatorImpl.prototype.calculateMovementRangeFrom_fwesud$ = function (hexagon, distance) {
    var tmp$, tmp$_0;
    var ret = HashSet_init();
    for (var x = -distance | 0; x <= distance; x++) {
      var a = -distance | 0;
      var b = (-x | 0) - distance | 0;
      tmp$ = Math_0.max(a, b);
      var b_0 = (-x | 0) + distance | 0;
      tmp$_0 = Math_0.min(distance, b_0);
      for (var y = tmp$; y <= tmp$_0; y++) {
        var z = (-x | 0) - y | 0;
        var tmpX = hexagon.gridX + x | 0;
        var tmpZ = hexagon.gridZ + z | 0;
        var tempCoordinate = CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(tmpX, tmpZ);
        if (this.hexagonalGrid_0.containsCubeCoordinate_eo2iv1$(tempCoordinate)) {
          var hex = this.hexagonalGrid_0.getByCubeCoordinate_eo2iv1$(tempCoordinate).get();
          ret.add_11rb$(hex);
        }
      }
    }
    return ret;
  };
  HexagonalGridCalculatorImpl.prototype.rotateHexagon_5mzewd$ = function (originalHex, targetHex, rotationDirection) {
    var diffX = targetHex.gridX - originalHex.gridX | 0;
    var diffZ = targetHex.gridZ - originalHex.gridZ | 0;
    var diffCoord = CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(diffX, diffZ);
    var rotatedCoord = rotationDirection.calculateRotation_eo2iv1$(diffCoord);
    var resultCoord = CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(originalHex.gridX + rotatedCoord.gridX | 0, originalHex.gridZ + rotatedCoord.gridZ | 0);
    return this.hexagonalGrid_0.getByCubeCoordinate_eo2iv1$(resultCoord);
  };
  HexagonalGridCalculatorImpl.prototype.calculateRingFrom_fwesud$ = function (centerHexagon, radius) {
    var result = HashSet_init();
    var neighborIndex = 0;
    var currentHexagon = centerHexagon;
    for (var i = 0; i < radius; i++) {
      var neighbor = this.hexagonalGrid_0.getNeighborByIndex_fwesud$(currentHexagon, neighborIndex);
      if (neighbor.isPresent) {
        currentHexagon = neighbor.get();
      }
       else {
        return result;
      }
    }
    return result;
  };
  HexagonalGridCalculatorImpl.prototype.drawLine_2pi2gm$ = function (from, to) {
    var distance = this.calculateDistanceBetween_2pi2gm$(from, to);
    var results = ArrayList_init();
    if (distance === 0) {
      return results;
    }
    for (var i = 0; i <= distance; i++) {
      var interpolatedCoordinate = this.cubeLinearInterpolate_0(from.cubeCoordinate, to.cubeCoordinate, 1.0 / distance * i);
      results.add_11rb$(this.hexagonalGrid_0.getByCubeCoordinate_eo2iv1$(interpolatedCoordinate).get());
    }
    return results;
  };
  HexagonalGridCalculatorImpl.prototype.isVisible_2pi2gm$ = function (from, to) {
    var tmp$;
    var traversePath = this.drawLine_2pi2gm$(from, to);
    tmp$ = traversePath.iterator();
    while (tmp$.hasNext()) {
      var pathHexagon = tmp$.next();
      if (equals(pathHexagon, from) || equals(pathHexagon, to)) {
        continue;
      }
      if (pathHexagon.satelliteData.isPresent && pathHexagon.satelliteData.get().opaque) {
        return false;
      }
    }
    return true;
  };
  HexagonalGridCalculatorImpl.prototype.cubeLinearInterpolate_0 = function (from, to, sample) {
    return this.roundToCubeCoordinate_0(this.linearInterpolate_0(from.gridX, to.gridX, sample), this.linearInterpolate_0(from.gridY, to.gridY, sample), this.linearInterpolate_0(from.gridZ, to.gridZ, sample));
  };
  HexagonalGridCalculatorImpl.prototype.linearInterpolate_0 = function (from, to, sample) {
    return from + (to - from | 0) * sample;
  };
  HexagonalGridCalculatorImpl.prototype.roundToCubeCoordinate_0 = function (gridX, gridY, gridZ) {
    var rx = numberToInt(round(gridX));
    var ry = numberToInt(round(gridY));
    var rz = numberToInt(round(gridZ));
    var x = rx - gridX;
    var differenceX = Math_0.abs(x);
    var x_0 = ry - gridY;
    var differenceY = Math_0.abs(x_0);
    var x_1 = rz - gridZ;
    var differenceZ = Math_0.abs(x_1);
    if (differenceX > differenceY && differenceX > differenceZ) {
      rx = (-ry | 0) - rz | 0;
    }
     else if (differenceY <= differenceZ) {
      rz = (-rx | 0) - ry | 0;
    }
    return CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(rx, rz);
  };
  HexagonalGridCalculatorImpl.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexagonalGridCalculatorImpl',
    interfaces: [HexagonalGridCalculator]
  };
  function HexagonalGridImpl(builder) {
    HexagonalGridImpl$Companion_getInstance();
    this.gridData_hiaeqc$_0 = builder.gridData;
    this.hexagonDataStorage_0 = builder.getHexagonDataStorage();
    var tmp$;
    tmp$ = builder.gridLayoutStrategy.fetchGridCoordinates_dx2ovm$(builder).iterator();
    while (tmp$.hasNext()) {
      var cubeCoordinate = tmp$.next();
      this.hexagonDataStorage_0.addCoordinate_eo2iv1$(cubeCoordinate);
    }
  }
  Object.defineProperty(HexagonalGridImpl.prototype, 'gridData', {
    get: function () {
      return this.gridData_hiaeqc$_0;
    }
  });
  function HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral(closure$coordIter, this$HexagonalGridImpl) {
    this.closure$coordIter = closure$coordIter;
    this.this$HexagonalGridImpl = this$HexagonalGridImpl;
  }
  function HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral$iterator$ObjectLiteral(closure$coordIter, this$HexagonalGridImpl) {
    this.closure$coordIter = closure$coordIter;
    this.this$HexagonalGridImpl = this$HexagonalGridImpl;
  }
  HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral$iterator$ObjectLiteral.prototype.hasNext = function () {
    return this.closure$coordIter.hasNext();
  };
  HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral$iterator$ObjectLiteral.prototype.next = function () {
    return new HexagonImpl(this.this$HexagonalGridImpl.gridData, this.closure$coordIter.next(), this.this$HexagonalGridImpl.hexagonDataStorage_0);
  };
  HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral$iterator$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Iterator]
  };
  HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral.prototype.iterator = function () {
    return new HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral$iterator$ObjectLiteral(this.closure$coordIter, this.this$HexagonalGridImpl);
  };
  HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Iterable]
  };
  Object.defineProperty(HexagonalGridImpl.prototype, 'hexagons', {
    get: function () {
      var coordIter = this.hexagonDataStorage_0.coordinates.iterator();
      return new HexagonalGridImpl$get_HexagonalGridImpl$hexagons$ObjectLiteral(coordIter, this);
    }
  });
  function HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral(closure$coordIter, this$HexagonalGridImpl) {
    this.closure$coordIter = closure$coordIter;
    this.this$HexagonalGridImpl = this$HexagonalGridImpl;
  }
  function HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral$iterator$ObjectLiteral(closure$coordIter, this$HexagonalGridImpl) {
    this.closure$coordIter = closure$coordIter;
    this.this$HexagonalGridImpl = this$HexagonalGridImpl;
  }
  HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral$iterator$ObjectLiteral.prototype.hasNext = function () {
    return this.closure$coordIter.hasNext();
  };
  HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral$iterator$ObjectLiteral.prototype.next = function () {
    return this.this$HexagonalGridImpl.getByCubeCoordinate_eo2iv1$(this.closure$coordIter.next()).get();
  };
  HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral$iterator$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Iterator]
  };
  HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral.prototype.iterator = function () {
    return new HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral$iterator$ObjectLiteral(this.closure$coordIter, this.this$HexagonalGridImpl);
  };
  HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Iterable]
  };
  HexagonalGridImpl.prototype.getHexagonsByCubeRange_bou2mm$ = function (from, to) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var coordinates = ArrayList_init();
    tmp$ = from.gridZ;
    tmp$_0 = to.gridZ;
    for (var gridZ = tmp$; gridZ <= tmp$_0; gridZ++) {
      tmp$_1 = from.gridX;
      tmp$_2 = to.gridX;
      for (var gridX = tmp$_1; gridX <= tmp$_2; gridX++) {
        coordinates.add_11rb$(CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(gridX, gridZ));
      }
    }
    var iter = coordinates.iterator();
    while (iter.hasNext()) {
      var next = iter.next();
      if (!this.containsCubeCoordinate_eo2iv1$(next)) {
        iter.remove();
      }
    }
    var coordIter = coordinates.iterator();
    return new HexagonalGridImpl$getHexagonsByCubeRange$ObjectLiteral(coordIter, this);
  };
  function HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral(closure$coordIter, this$HexagonalGridImpl) {
    this.closure$coordIter = closure$coordIter;
    this.this$HexagonalGridImpl = this$HexagonalGridImpl;
  }
  function HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral$iterator$ObjectLiteral(closure$coordIter, this$HexagonalGridImpl) {
    this.closure$coordIter = closure$coordIter;
    this.this$HexagonalGridImpl = this$HexagonalGridImpl;
  }
  HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral$iterator$ObjectLiteral.prototype.hasNext = function () {
    return this.closure$coordIter.hasNext();
  };
  HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral$iterator$ObjectLiteral.prototype.next = function () {
    return this.this$HexagonalGridImpl.getByCubeCoordinate_eo2iv1$(this.closure$coordIter.next()).get();
  };
  HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral$iterator$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Iterator]
  };
  HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral.prototype.iterator = function () {
    return new HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral$iterator$ObjectLiteral(this.closure$coordIter, this.this$HexagonalGridImpl);
  };
  HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Iterable]
  };
  HexagonalGridImpl.prototype.getHexagonsByOffsetRange_tjonv8$ = function (gridXFrom, gridXTo, gridYFrom, gridYTo) {
    var coords = ArrayList_init();
    for (var gridX = gridXFrom; gridX <= gridXTo; gridX++) {
      for (var gridY = gridYFrom; gridY <= gridYTo; gridY++) {
        var cubeX = CoordinateConverter$Companion_getInstance().convertOffsetCoordinatesToCubeX_edbmww$(gridX, gridY, this.gridData.orientation);
        var cubeZ = CoordinateConverter$Companion_getInstance().convertOffsetCoordinatesToCubeZ_edbmww$(gridX, gridY, this.gridData.orientation);
        var coord = CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(cubeX, cubeZ);
        if (this.getByCubeCoordinate_eo2iv1$(coord).isPresent) {
          coords.add_11rb$(coord);
        }
      }
    }
    var coordIter = coords.iterator();
    return new HexagonalGridImpl$getHexagonsByOffsetRange$ObjectLiteral(coordIter, this);
  };
  HexagonalGridImpl.prototype.containsCubeCoordinate_eo2iv1$ = function (coordinate) {
    return this.hexagonDataStorage_0.containsCoordinate_eo2iv1$(coordinate);
  };
  HexagonalGridImpl.prototype.getByCubeCoordinate_eo2iv1$ = function (coordinate) {
    return this.containsCubeCoordinate_eo2iv1$(coordinate) ? Maybe$Companion_getInstance().of_mh5how$(new HexagonImpl(this.gridData, coordinate, this.hexagonDataStorage_0)) : Maybe$Companion_getInstance().empty_287e2$();
  };
  HexagonalGridImpl.prototype.getByPixelCoordinate_lu1900$ = function (coordinateX, coordinateY) {
    var tmp$;
    var estimatedGridX = numberToInt(coordinateX / this.gridData.hexagonWidth);
    var estimatedGridZ = numberToInt(coordinateY / this.gridData.hexagonHeight);
    estimatedGridX = CoordinateConverter$Companion_getInstance().convertOffsetCoordinatesToCubeX_edbmww$(estimatedGridX, estimatedGridZ, this.gridData.orientation);
    estimatedGridZ = CoordinateConverter$Companion_getInstance().convertOffsetCoordinatesToCubeZ_edbmww$(estimatedGridX, estimatedGridZ, this.gridData.orientation);
    var estimatedCoordinate = CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(estimatedGridX, estimatedGridZ);
    var tempHex = new HexagonImpl(this.gridData, estimatedCoordinate, this.hexagonDataStorage_0);
    var trueHex = this.refineHexagonByPixel_0(tempHex, Point$Companion_getInstance().fromPosition_lu1900$(coordinateX, coordinateY));
    if (this.hexagonsAreAtTheSamePosition_0(tempHex, trueHex)) {
      tmp$ = this.getByCubeCoordinate_eo2iv1$(estimatedCoordinate);
    }
     else {
      tmp$ = this.containsCubeCoordinate_eo2iv1$(trueHex.cubeCoordinate) ? Maybe$Companion_getInstance().of_mh5how$(trueHex) : Maybe$Companion_getInstance().empty_287e2$();
    }
    return tmp$;
  };
  HexagonalGridImpl.prototype.getNeighborByIndex_fwesud$ = function (hexagon, index) {
    var neighborGridX = hexagon.gridX + HexagonalGridImpl$Companion_getInstance().NEIGHBORS_0[index][0] | 0;
    var neighborGridZ = hexagon.gridZ + HexagonalGridImpl$Companion_getInstance().NEIGHBORS_0[index][1] | 0;
    var neighborCoordinate = CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(neighborGridX, neighborGridZ);
    return this.getByCubeCoordinate_eo2iv1$(neighborCoordinate);
  };
  HexagonalGridImpl.prototype.getNeighborsOf_svg8j1$ = function (hexagon) {
    var tmp$;
    var neighbors = HashSet_init();
    tmp$ = HexagonalGridImpl$Companion_getInstance().NEIGHBORS_0;
    for (var i = 0; i !== tmp$.length; ++i) {
      var retHex = this.getNeighborByIndex_fwesud$(hexagon, i);
      if (retHex.isPresent) {
        neighbors.add_11rb$(retHex.get());
      }
    }
    return neighbors;
  };
  HexagonalGridImpl.prototype.hexagonsAreAtTheSamePosition_0 = function (hex0, hex1) {
    return hex0.gridX === hex1.gridX && hex0.gridZ === hex1.gridZ;
  };
  HexagonalGridImpl.prototype.refineHexagonByPixel_0 = function (hexagon, clickedPoint) {
    var tmp$;
    var refined = hexagon;
    var smallestDistance = clickedPoint.distanceFrom_ievl7k$(Point$Companion_getInstance().fromPosition_lu1900$(refined.centerX, refined.centerY));
    tmp$ = this.getNeighborsOf_svg8j1$(hexagon).iterator();
    while (tmp$.hasNext()) {
      var neighbor = tmp$.next();
      var currentDistance = clickedPoint.distanceFrom_ievl7k$(Point$Companion_getInstance().fromPosition_lu1900$(neighbor.centerX, neighbor.centerY));
      if (currentDistance < smallestDistance) {
        refined = neighbor;
        smallestDistance = currentDistance;
      }
    }
    return refined;
  };
  function HexagonalGridImpl$Companion() {
    HexagonalGridImpl$Companion_instance = this;
    this.NEIGHBORS_0 = [new Int32Array([1, 0]), new Int32Array([1, -1]), new Int32Array([0, -1]), new Int32Array([-1, 0]), new Int32Array([-1, 1]), new Int32Array([0, 1])];
    this.NEIGHBOR_X_INDEX_0 = 0;
    this.NEIGHBOR_Z_INDEX_0 = 1;
  }
  HexagonalGridImpl$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var HexagonalGridImpl$Companion_instance = null;
  function HexagonalGridImpl$Companion_getInstance() {
    if (HexagonalGridImpl$Companion_instance === null) {
      new HexagonalGridImpl$Companion();
    }
    return HexagonalGridImpl$Companion_instance;
  }
  HexagonalGridImpl.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexagonalGridImpl',
    interfaces: [HexagonalGrid]
  };
  function GridLayoutStrategy() {
  }
  GridLayoutStrategy.prototype.checkCommonCase_vux9f0$ = function (gridHeight, gridWidth) {
    return gridHeight > 0 && gridWidth > 0;
  };
  GridLayoutStrategy.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'GridLayoutStrategy',
    interfaces: []
  };
  function HexagonalGridLayoutStrategy() {
    GridLayoutStrategy.call(this);
  }
  HexagonalGridLayoutStrategy.prototype.fetchGridCoordinates_dx2ovm$ = function (builder) {
    var tmp$, tmp$_0;
    var coords = ArrayList_init();
    var gridSize = builder.getGridHeight();
    var tmp$_1;
    if (HexagonOrientation$FLAT_TOP_getInstance().equals(builder.getOrientation())) {
      var x = gridSize / 2.0;
      tmp$_1 = numberToInt(Math_0.floor(x));
    }
     else
      tmp$_1 = numberToInt(round(gridSize / 4.0));
    var startX = tmp$_1;
    var x_0 = gridSize / 2.0;
    var hexRadius = numberToInt(Math_0.floor(x_0));
    var minX = startX - hexRadius | 0;
    var y = 0;
    while (y < gridSize) {
      var distanceFromMid = abs(hexRadius - y | 0);
      var a = startX;
      tmp$ = Math_0.max(a, minX);
      var a_0 = startX;
      tmp$_0 = Math_0.max(a_0, minX) + hexRadius + hexRadius - distanceFromMid | 0;
      for (var x_1 = tmp$; x_1 <= tmp$_0; x_1++) {
        var tmp$_2;
        if (HexagonOrientation$FLAT_TOP_getInstance().equals(builder.getOrientation())) {
          var tmp$_3 = y;
          var x_2 = gridSize / 4.0;
          tmp$_2 = tmp$_3 - numberToInt(Math_0.floor(x_2)) | 0;
        }
         else
          tmp$_2 = y;
        var gridZ = tmp$_2;
        coords.add_11rb$(CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(x_1, gridZ));
      }
      startX = startX - 1 | 0;
      y = y + 1 | 0;
    }
    return coords;
  };
  HexagonalGridLayoutStrategy.prototype.checkParameters_vux9f0$ = function (gridHeight, gridWidth) {
    var superResult = this.checkCommonCase_vux9f0$(gridHeight, gridWidth);
    var result = gridHeight === gridWidth && abs(gridHeight % 2) === 1;
    return result && superResult;
  };
  HexagonalGridLayoutStrategy.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'HexagonalGridLayoutStrategy',
    interfaces: [GridLayoutStrategy]
  };
  function RectangularGridLayoutStrategy() {
    GridLayoutStrategy.call(this);
  }
  RectangularGridLayoutStrategy.prototype.fetchGridCoordinates_dx2ovm$ = function (builder) {
    var tmp$, tmp$_0;
    var coords = ArrayList_init();
    tmp$ = builder.getGridHeight();
    for (var y = 0; y < tmp$; y++) {
      tmp$_0 = builder.getGridWidth();
      for (var x = 0; x < tmp$_0; x++) {
        var gridX = CoordinateConverter$Companion_getInstance().convertOffsetCoordinatesToCubeX_edbmww$(x, y, builder.getOrientation());
        var gridZ = CoordinateConverter$Companion_getInstance().convertOffsetCoordinatesToCubeZ_edbmww$(x, y, builder.getOrientation());
        coords.add_11rb$(CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(gridX, gridZ));
      }
    }
    return coords;
  };
  RectangularGridLayoutStrategy.prototype.checkParameters_vux9f0$ = function (gridHeight, gridWidth) {
    return this.checkCommonCase_vux9f0$(gridHeight, gridWidth);
  };
  RectangularGridLayoutStrategy.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'RectangularGridLayoutStrategy',
    interfaces: [GridLayoutStrategy]
  };
  function TrapezoidGridLayoutStrategy() {
    GridLayoutStrategy.call(this);
  }
  TrapezoidGridLayoutStrategy.prototype.fetchGridCoordinates_dx2ovm$ = function (builder) {
    var tmp$, tmp$_0;
    var coords = ArrayList_init();
    tmp$ = builder.getGridHeight();
    for (var gridZ = 0; gridZ < tmp$; gridZ++) {
      tmp$_0 = builder.getGridWidth();
      for (var gridX = 0; gridX < tmp$_0; gridX++) {
        coords.add_11rb$(CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(gridX, gridZ));
      }
    }
    return coords;
  };
  TrapezoidGridLayoutStrategy.prototype.checkParameters_vux9f0$ = function (gridHeight, gridWidth) {
    return this.checkCommonCase_vux9f0$(gridHeight, gridWidth);
  };
  TrapezoidGridLayoutStrategy.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TrapezoidGridLayoutStrategy',
    interfaces: [GridLayoutStrategy]
  };
  function TriangularGridLayoutStrategy() {
    GridLayoutStrategy.call(this);
  }
  TriangularGridLayoutStrategy.prototype.fetchGridCoordinates_dx2ovm$ = function (builder) {
    var coords = ArrayList_init();
    var gridSize = builder.getGridHeight();
    for (var gridZ = 0; gridZ < gridSize; gridZ++) {
      var endX = gridSize - gridZ | 0;
      for (var gridX = 0; gridX < endX; gridX++) {
        coords.add_11rb$(CubeCoordinate$Companion_getInstance().fromCoordinates_vux9f0$(gridX, gridZ));
      }
    }
    return coords;
  };
  TriangularGridLayoutStrategy.prototype.checkParameters_vux9f0$ = function (gridHeight, gridWidth) {
    var superResult = this.checkCommonCase_vux9f0$(gridHeight, gridWidth);
    var result = gridHeight === gridWidth;
    return superResult && result;
  };
  TriangularGridLayoutStrategy.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'TriangularGridLayoutStrategy',
    interfaces: [GridLayoutStrategy]
  };
  function Consumer() {
  }
  Consumer.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Consumer',
    interfaces: []
  };
  function Function() {
  }
  Function.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Function',
    interfaces: []
  };
  function Maybe() {
    Maybe$Companion_getInstance();
    this.value_0 = null;
  }
  Object.defineProperty(Maybe.prototype, 'isPresent', {
    get: function () {
      return this.value_0 != null;
    }
  });
  Maybe.prototype.isEmpty = function () {
    return !this.isPresent;
  };
  Maybe.prototype.get = function () {
    if (this.value_0 == null) {
      throw new NoSuchElementException('No value present');
    }
    return this.value_0;
  };
  Maybe.prototype.ifPresent_w6nq0$ = function (consumer) {
    if (this.value_0 != null)
      consumer.accept_11rb$(this.value_0);
  };
  Maybe.prototype.filter_xiw3fv$ = function (predicate) {
    return !this.isPresent ? this : predicate.test_11rb$(this.get()) ? this : Maybe$Companion_getInstance().empty_287e2$();
  };
  Maybe.prototype.map_ixhylx$ = function (mapper) {
    var tmp$;
    if (this.isEmpty())
      tmp$ = Maybe$Companion_getInstance().empty_287e2$();
    else {
      tmp$ = Maybe$Companion_getInstance().ofNullable_mh5how$(mapper.apply_11rb$(this.get()));
    }
    return tmp$;
  };
  Maybe.prototype.flatMap_yq4cf5$ = function (mapper) {
    var tmp$;
    if (this.isEmpty())
      tmp$ = Maybe$Companion_getInstance().empty_287e2$();
    else {
      tmp$ = mapper.apply_11rb$(this.get());
    }
    return tmp$;
  };
  Maybe.prototype.fold_d5ec4a$ = function (whenEmpty, whenPresent) {
    var tmp$;
    if (this.isPresent) {
      tmp$ = whenPresent.apply_11rb$(this.get());
    }
     else {
      tmp$ = whenEmpty.get();
    }
    return tmp$;
  };
  Maybe.prototype.orElse_11rb$ = function (other) {
    var tmp$;
    return (tmp$ = this.value_0) != null ? tmp$ : other;
  };
  Maybe.prototype.orElseGet_7e9ozg$ = function (other) {
    var tmp$;
    return (tmp$ = this.value_0) != null ? tmp$ : other.get();
  };
  Maybe.prototype.orElseThrow_jkdim0$ = function (exceptionSupplier) {
    var tmp$;
    tmp$ = this.value_0;
    if (tmp$ == null) {
      throw exceptionSupplier.get();
    }
    return tmp$;
  };
  Maybe.prototype.equals = function (other) {
    var tmp$;
    if (this === other) {
      return true;
    }
    if (!Kotlin.isType(other, Maybe)) {
      return false;
    }
    var otherMaybe = (tmp$ = other) == null || Kotlin.isType(tmp$, Maybe) ? tmp$ : throwCCE();
    return equals(this.value_0, ensureNotNull(otherMaybe).value_0);
  };
  Maybe.prototype.hashCode = function () {
    var tmp$, tmp$_0;
    return (tmp$_0 = (tmp$ = this.value_0) != null ? hashCode(tmp$) : null) != null ? tmp$_0 : 0;
  };
  Maybe.prototype.toString = function () {
    return this.value_0 != null ? 'Maybe[' + toString(this.value_0) + ']' : 'Maybe.empty';
  };
  function Maybe$Companion() {
    Maybe$Companion_instance = this;
    this.EMPTY_0 = Maybe_init();
  }
  Maybe$Companion.prototype.empty_287e2$ = function () {
    var tmp$;
    return Kotlin.isType(tmp$ = this.EMPTY_0, Maybe) ? tmp$ : throwCCE();
  };
  Maybe$Companion.prototype.of_mh5how$ = function (value) {
    return Maybe_init_0(value);
  };
  Maybe$Companion.prototype.ofNullable_mh5how$ = function (value) {
    return value == null ? this.empty_287e2$() : this.of_mh5how$(value);
  };
  Maybe$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Maybe$Companion_instance = null;
  function Maybe$Companion_getInstance() {
    if (Maybe$Companion_instance === null) {
      new Maybe$Companion();
    }
    return Maybe$Companion_instance;
  }
  Maybe.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Maybe',
    interfaces: []
  };
  function Maybe_init($this) {
    $this = $this || Object.create(Maybe.prototype);
    Maybe.call($this);
    $this.value_0 = null;
    return $this;
  }
  function Maybe_init_0(value, $this) {
    $this = $this || Object.create(Maybe.prototype);
    Maybe.call($this);
    $this.value_0 = value;
    return $this;
  }
  function Predicate() {
    Predicate$Companion_getInstance();
  }
  function Predicate$and$ObjectLiteral(closure$outer, closure$other) {
    this.closure$outer = closure$outer;
    this.closure$other = closure$other;
  }
  Predicate$and$ObjectLiteral.prototype.test_11rb$ = function (t) {
    return this.closure$outer.test_11rb$(t) && this.closure$other.test_11rb$(t);
  };
  Predicate$and$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Predicate]
  };
  Predicate.prototype.and_xiw3fv$ = function (other) {
    var outer = this;
    return new Predicate$and$ObjectLiteral(outer, other);
  };
  function Predicate$negate$ObjectLiteral(closure$outer) {
    this.closure$outer = closure$outer;
  }
  Predicate$negate$ObjectLiteral.prototype.test_11rb$ = function (t) {
    return !this.closure$outer.test_11rb$(t);
  };
  Predicate$negate$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Predicate]
  };
  Predicate.prototype.negate = function () {
    var outer = this;
    return new Predicate$negate$ObjectLiteral(outer);
  };
  function Predicate$or$ObjectLiteral(closure$outer, closure$other) {
    this.closure$outer = closure$outer;
    this.closure$other = closure$other;
  }
  Predicate$or$ObjectLiteral.prototype.test_11rb$ = function (t) {
    return this.closure$outer.test_11rb$(t) || this.closure$other.test_11rb$(t);
  };
  Predicate$or$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Predicate]
  };
  Predicate.prototype.or_xiw3fv$ = function (other) {
    var outer = this;
    return new Predicate$or$ObjectLiteral(outer, other);
  };
  function Predicate$Companion() {
    Predicate$Companion_instance = this;
  }
  function Predicate$Companion$isEqual$ObjectLiteral(closure$targetRef) {
    this.closure$targetRef = closure$targetRef;
  }
  Predicate$Companion$isEqual$ObjectLiteral.prototype.test_11rb$ = function (t) {
    return equals(this.closure$targetRef, t);
  };
  Predicate$Companion$isEqual$ObjectLiteral.$metadata$ = {
    kind: Kind_CLASS,
    interfaces: [Predicate]
  };
  Predicate$Companion.prototype.isEqual_86eaif$ = function (targetRef) {
    return new Predicate$Companion$isEqual$ObjectLiteral(targetRef);
  };
  Predicate$Companion.$metadata$ = {
    kind: Kind_OBJECT,
    simpleName: 'Companion',
    interfaces: []
  };
  var Predicate$Companion_instance = null;
  function Predicate$Companion_getInstance() {
    if (Predicate$Companion_instance === null) {
      new Predicate$Companion();
    }
    return Predicate$Companion_instance;
  }
  Predicate.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Predicate',
    interfaces: []
  };
  function Supplier() {
  }
  Supplier.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'Supplier',
    interfaces: []
  };
  Object.defineProperty(CoordinateConverter, 'Companion', {
    get: CoordinateConverter$Companion_getInstance
  });
  var package$org = _.org || (_.org = {});
  var package$hexworks = package$org.hexworks || (package$org.hexworks = {});
  var package$mixite = package$hexworks.mixite || (package$hexworks.mixite = {});
  var package$core = package$mixite.core || (package$mixite.core = {});
  var package$api = package$core.api || (package$core.api = {});
  package$api.CoordinateConverter = CoordinateConverter;
  Object.defineProperty(CubeCoordinate, 'Companion', {
    get: CubeCoordinate$Companion_getInstance
  });
  package$api.CubeCoordinate = CubeCoordinate;
  package$api.Hexagon = Hexagon;
  Object.defineProperty(HexagonOrientation, 'POINTY_TOP', {
    get: HexagonOrientation$POINTY_TOP_getInstance
  });
  Object.defineProperty(HexagonOrientation, 'FLAT_TOP', {
    get: HexagonOrientation$FLAT_TOP_getInstance
  });
  package$api.HexagonOrientation = HexagonOrientation;
  package$api.HexagonalGrid = HexagonalGrid;
  package$api.HexagonalGridBuilder = HexagonalGridBuilder;
  package$api.HexagonalGridCalculator = HexagonalGridCalculator;
  Object.defineProperty(HexagonalGridLayout, 'RECTANGULAR', {
    get: HexagonalGridLayout$RECTANGULAR_getInstance
  });
  Object.defineProperty(HexagonalGridLayout, 'HEXAGONAL', {
    get: HexagonalGridLayout$HEXAGONAL_getInstance
  });
  Object.defineProperty(HexagonalGridLayout, 'TRIANGULAR', {
    get: HexagonalGridLayout$TRIANGULAR_getInstance
  });
  Object.defineProperty(HexagonalGridLayout, 'TRAPEZOID', {
    get: HexagonalGridLayout$TRAPEZOID_getInstance
  });
  package$api.HexagonalGridLayout = HexagonalGridLayout;
  Object.defineProperty(Point, 'Companion', {
    get: Point$Companion_getInstance
  });
  package$api.Point = Point;
  package$api.Rectangle_init_6y0v78$ = Rectangle_init;
  package$api.Rectangle_init_fvnklb$ = Rectangle_init_0;
  package$api.Rectangle = Rectangle;
  Object.defineProperty(RotationDirection, 'RIGHT', {
    get: RotationDirection$RIGHT_getInstance
  });
  Object.defineProperty(RotationDirection, 'LEFT', {
    get: RotationDirection$LEFT_getInstance
  });
  RotationDirection.RotationCalculator = RotationDirection$RotationCalculator;
  package$api.RotationDirection = RotationDirection;
  var package$contract = package$api.contract || (package$api.contract = {});
  package$contract.HexagonDataStorage = HexagonDataStorage;
  package$contract.SatelliteData = SatelliteData;
  var package$defaults = package$api.defaults || (package$api.defaults = {});
  package$defaults.DefaultHexagonDataStorage = DefaultHexagonDataStorage;
  package$defaults.DefaultSatelliteData = DefaultSatelliteData;
  var package$internal = package$core.internal || (package$core.internal = {});
  package$internal.GridData = GridData;
  var package$impl = package$internal.impl || (package$internal.impl = {});
  package$impl.HexagonImpl = HexagonImpl;
  package$impl.HexagonalGridCalculatorImpl = HexagonalGridCalculatorImpl;
  Object.defineProperty(HexagonalGridImpl, 'Companion', {
    get: HexagonalGridImpl$Companion_getInstance
  });
  package$impl.HexagonalGridImpl = HexagonalGridImpl;
  var package$layoutstrategy = package$impl.layoutstrategy || (package$impl.layoutstrategy = {});
  package$layoutstrategy.GridLayoutStrategy = GridLayoutStrategy;
  package$layoutstrategy.HexagonalGridLayoutStrategy = HexagonalGridLayoutStrategy;
  package$layoutstrategy.RectangularGridLayoutStrategy = RectangularGridLayoutStrategy;
  package$layoutstrategy.TrapezoidGridLayoutStrategy = TrapezoidGridLayoutStrategy;
  package$layoutstrategy.TriangularGridLayoutStrategy = TriangularGridLayoutStrategy;
  var package$vendor = package$core.vendor || (package$core.vendor = {});
  package$vendor.Consumer = Consumer;
  package$vendor.Function = Function;
  Object.defineProperty(Maybe, 'Companion', {
    get: Maybe$Companion_getInstance
  });
  package$vendor.Maybe = Maybe;
  Object.defineProperty(Predicate, 'Companion', {
    get: Predicate$Companion_getInstance
  });
  package$vendor.Predicate = Predicate;
  package$vendor.Supplier = Supplier;
  Predicate$and$ObjectLiteral.prototype.and_xiw3fv$ = Predicate.prototype.and_xiw3fv$;
  Predicate$and$ObjectLiteral.prototype.negate = Predicate.prototype.negate;
  Predicate$and$ObjectLiteral.prototype.or_xiw3fv$ = Predicate.prototype.or_xiw3fv$;
  Predicate$negate$ObjectLiteral.prototype.and_xiw3fv$ = Predicate.prototype.and_xiw3fv$;
  Predicate$negate$ObjectLiteral.prototype.negate = Predicate.prototype.negate;
  Predicate$negate$ObjectLiteral.prototype.or_xiw3fv$ = Predicate.prototype.or_xiw3fv$;
  Predicate$or$ObjectLiteral.prototype.and_xiw3fv$ = Predicate.prototype.and_xiw3fv$;
  Predicate$or$ObjectLiteral.prototype.negate = Predicate.prototype.negate;
  Predicate$or$ObjectLiteral.prototype.or_xiw3fv$ = Predicate.prototype.or_xiw3fv$;
  Predicate$Companion$isEqual$ObjectLiteral.prototype.and_xiw3fv$ = Predicate.prototype.and_xiw3fv$;
  Predicate$Companion$isEqual$ObjectLiteral.prototype.negate = Predicate.prototype.negate;
  Predicate$Companion$isEqual$ObjectLiteral.prototype.or_xiw3fv$ = Predicate.prototype.or_xiw3fv$;
  Kotlin.defineModule('mixite.core', _);
  return _;
}(typeof this['mixite.core'] === 'undefined' ? {} : this['mixite.core'], kotlin);

//# sourceMappingURL=mixite.core.js.map
