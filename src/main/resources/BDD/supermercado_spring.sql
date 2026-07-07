-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-07-2026 a las 15:38:52
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `supermercado_spring`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id_detalleventa` bigint(20) NOT NULL,
  `dv_cant_product` int(11) NOT NULL,
  `dv_precio_product` decimal(11,2) NOT NULL,
  `subtotal` decimal(19,2) NOT NULL,
  `producto_id_producto` bigint(20) NOT NULL,
  `venta_id_venta` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `detalle_venta`
--

INSERT INTO `detalle_venta` (`id_detalleventa`, `dv_cant_product`, `dv_precio_product`, `subtotal`, `producto_id_producto`, `venta_id_venta`) VALUES
(41, 1, 1800.00, 1800.00, 1, 1),
(42, 1, 1200.00, 1200.00, 2, 2),
(43, 1, 3200.00, 3200.00, 3, 3),
(44, 1, 1300.00, 1300.00, 4, 4),
(45, 1, 2500.00, 2500.00, 5, 5),
(46, 1, 1000.00, 1000.00, 6, 6),
(47, 1, 1800.00, 1800.00, 7, 7),
(48, 1, 6500.00, 6500.00, 8, 8),
(49, 1, 2400.00, 2400.00, 9, 9),
(50, 1, 1500.00, 1500.00, 10, 10),
(51, 1, 4800.00, 4800.00, 11, 11),
(52, 1, 2200.00, 2200.00, 12, 12),
(53, 1, 7200.00, 7200.00, 13, 13),
(54, 1, 3900.00, 3900.00, 14, 14),
(55, 1, 8400.00, 8400.00, 15, 15),
(56, 1, 1700.00, 1700.00, 16, 16),
(57, 1, 1600.00, 1600.00, 17, 17),
(58, 1, 1400.00, 1400.00, 18, 18),
(59, 1, 2100.00, 2100.00, 19, 19),
(60, 1, 4500.00, 4500.00, 20, 20),
(61, 1, 1100.00, 1100.00, 21, 21),
(62, 1, 900.00, 900.00, 22, 22),
(63, 1, 2600.00, 2600.00, 23, 23),
(64, 1, 1900.00, 1900.00, 24, 24),
(65, 1, 2300.00, 2300.00, 25, 25),
(66, 1, 12500.00, 12500.00, 26, 26),
(67, 1, 7800.00, 7800.00, 27, 27),
(68, 1, 5600.00, 5600.00, 28, 28),
(69, 1, 3400.00, 3400.00, 29, 29),
(70, 1, 45000.00, 45000.00, 30, 30),
(71, 1, 1800.00, 1800.00, 1, 31),
(72, 1, 1200.00, 1200.00, 2, 32),
(73, 1, 3200.00, 3200.00, 3, 33),
(74, 1, 1300.00, 1300.00, 4, 34),
(75, 1, 2500.00, 2500.00, 5, 35),
(76, 1, 1000.00, 1000.00, 6, 36),
(77, 1, 1800.00, 1800.00, 7, 37),
(78, 1, 6500.00, 6500.00, 8, 38),
(79, 1, 2400.00, 2400.00, 9, 39),
(80, 1, 1500.00, 1500.00, 10, 40);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id_producto` bigint(20) NOT NULL,
  `cant_producto` bigint(20) NOT NULL,
  `categoria_producto` enum('AGUAS','ALMACEN','BAZAR','BEBES','BEBIDAS','CARNES','CERVEZAS','CONGELADOS','DESAYUNO_Y_MERIENDA','ELECTRODOMESTICOS','FIAMBRES','FRUTAS','GASEOSAS','GOLOSINAS','HIGIENE_PERSONAL','JUGOS','LACTEOS','LIBRERIA','LIMPIEZA','MASCOTAS','PANADERIA','PASTAS','PERFUMERIA','PESCADOS','POLLO','QUESOS','SNACKS','VERDURAS','VINOS') NOT NULL,
  `nombre_producto` varchar(50) NOT NULL,
  `precio_producto` decimal(20,2) NOT NULL,
  `version` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id_producto`, `cant_producto`, `categoria_producto`, `nombre_producto`, `precio_producto`, `version`) VALUES
(1, 298, 'ALMACEN', 'Arroz 1kg', 1800.00, 0),
(2, 298, 'ALMACEN', 'Fideos spaghetti 500g', 1200.00, 0),
(3, 298, 'ALMACEN', 'Aceite girasol 900ml', 3200.00, 0),
(4, 298, 'BEBIDAS', 'Agua saborizada lima 1.5L', 1300.00, 0),
(5, 298, 'GASEOSAS', 'Gaseosa cola 2.25L', 2500.00, 0),
(6, 298, 'AGUAS', 'Agua mineral 1.5L', 1000.00, 0),
(7, 298, 'JUGOS', 'Jugo naranja 1L', 1800.00, 0),
(8, 298, 'VINOS', 'Vino tinto 750ml', 6500.00, 0),
(9, 298, 'CERVEZAS', 'Cerveza rubia 1L', 2400.00, 0),
(10, 298, 'LACTEOS', 'Leche entera 1L', 1500.00, 0),
(11, 299, 'QUESOS', 'Queso cremoso 500g', 4800.00, 0),
(12, 299, 'FIAMBRES', 'Jamon cocido 200g', 2200.00, 0),
(13, 299, 'CARNES', 'Carne picada 1kg', 7200.00, 0),
(14, 299, 'POLLO', 'Pollo entero 1kg', 3900.00, 0),
(15, 299, 'PESCADOS', 'Filet de merluza 1kg', 8400.00, 0),
(16, 299, 'FRUTAS', 'Manzana 1kg', 1700.00, 0),
(17, 299, 'VERDURAS', 'Tomate 1kg', 1600.00, 0),
(18, 299, 'PANADERIA', 'Pan lactal 500g', 1400.00, 0),
(19, 299, 'PASTAS', 'Ravioles 500g', 2100.00, 0),
(20, 299, 'CONGELADOS', 'Nuggets 1kg', 4500.00, 0),
(21, 299, 'SNACKS', 'Papas fritas 150g', 1100.00, 0),
(22, 299, 'GOLOSINAS', 'Galletitas dulces', 900.00, 0),
(23, 299, 'DESAYUNO_Y_MERIENDA', 'Cereal avena', 2600.00, 0),
(24, 299, 'LIMPIEZA', 'Detergente 1L', 1900.00, 0),
(25, 299, 'HIGIENE_PERSONAL', 'Shampoo 400ml', 2300.00, 0),
(26, 299, 'PERFUMERIA', 'Perfume 90ml', 12500.00, 0),
(27, 299, 'BEBES', 'Panal 30u', 7800.00, 0),
(28, 299, 'MASCOTAS', 'Alimento para perros 3kg', 5600.00, 0),
(29, 299, 'BAZAR', 'Juego de vasos 6u', 3400.00, 0),
(30, 299, 'ELECTRODOMESTICOS', 'Microondas 20L', 45000.00, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `id_sucursal` bigint(20) NOT NULL,
  `direccion_sucursal` varchar(50) NOT NULL,
  `nombre_sucursal` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`id_sucursal`, `direccion_sucursal`, `nombre_sucursal`) VALUES
(1, 'Av. Corrientes 1000', 'Sucursal Centro'),
(2, 'Av. Cabildo 2200', 'Sucursal Norte'),
(3, 'Av. Roca 3300', 'Sucursal Sur'),
(4, 'Av. Rivadavia 4400', 'Sucursal Oeste'),
(5, 'Av. Belgrano 5500', 'Sucursal Este');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE `venta` (
  `id_venta` bigint(20) NOT NULL,
  `estado_venta` enum('CANCELADA','COMPLETADA','CONFIRMADA','ENTREGADA','LISTA_PARA_ENTREGA','PAGADA','PENDIENTE','PREPARANDO','RECHAZADA','REEMBOLSADA') NOT NULL,
  `fecha_venta` datetime(6) NOT NULL,
  `total_venta` decimal(19,2) NOT NULL,
  `sucursal_id_sucursal` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`id_venta`, `estado_venta`, `fecha_venta`, `total_venta`, `sucursal_id_sucursal`) VALUES
(1, 'PAGADA', '2026-07-07 08:01:00.000000', 1800.00, 1),
(2, 'PAGADA', '2026-07-07 08:02:00.000000', 1200.00, 2),
(3, 'PAGADA', '2026-07-07 08:03:00.000000', 3200.00, 3),
(4, 'PAGADA', '2026-07-07 08:04:00.000000', 1300.00, 4),
(5, 'PAGADA', '2026-07-07 08:05:00.000000', 2500.00, 5),
(6, 'PAGADA', '2026-07-07 08:06:00.000000', 1000.00, 1),
(7, 'PAGADA', '2026-07-07 08:07:00.000000', 1800.00, 2),
(8, 'PAGADA', '2026-07-07 08:08:00.000000', 6500.00, 3),
(9, 'PAGADA', '2026-07-07 08:09:00.000000', 2400.00, 4),
(11, 'PAGADA', '2026-07-07 08:11:00.000000', 4800.00, 1),
(12, 'PAGADA', '2026-07-07 08:12:00.000000', 2200.00, 2),
(13, 'PAGADA', '2026-07-07 08:13:00.000000', 7200.00, 3),
(14, 'PAGADA', '2026-07-07 08:14:00.000000', 3900.00, 4),
(15, 'PAGADA', '2026-07-07 08:15:00.000000', 8400.00, 5),
(16, 'PAGADA', '2026-07-07 08:16:00.000000', 1700.00, 1),
(17, 'PAGADA', '2026-07-07 08:17:00.000000', 1600.00, 2),
(18, 'PAGADA', '2026-07-07 08:18:00.000000', 1400.00, 3),
(19, 'PAGADA', '2026-07-07 08:19:00.000000', 2100.00, 4),
(21, 'PAGADA', '2026-07-07 08:21:00.000000', 1100.00, 1),
(22, 'PAGADA', '2026-07-07 08:22:00.000000', 900.00, 2),
(23, 'PAGADA', '2026-07-07 08:23:00.000000', 2600.00, 3),
(24, 'PAGADA', '2026-07-07 08:24:00.000000', 1900.00, 4),
(25, 'PAGADA', '2026-07-07 08:25:00.000000', 2300.00, 5),
(26, 'PAGADA', '2026-07-07 08:26:00.000000', 12500.00, 1),
(27, 'PAGADA', '2026-07-07 08:27:00.000000', 7800.00, 2),
(28, 'PAGADA', '2026-07-07 08:28:00.000000', 5600.00, 3),
(29, 'PAGADA', '2026-07-07 08:29:00.000000', 3400.00, 4),
(31, 'PAGADA', '2026-07-07 08:31:00.000000', 1800.00, 1),
(32, 'PAGADA', '2026-07-07 08:32:00.000000', 1200.00, 2),
(33, 'PAGADA', '2026-07-07 08:33:00.000000', 3200.00, 3),
(34, 'PAGADA', '2026-07-07 08:34:00.000000', 1300.00, 4),
(35, 'PAGADA', '2026-07-07 08:35:00.000000', 2500.00, 5),
(36, 'PAGADA', '2026-07-07 08:36:00.000000', 1000.00, 1),
(37, 'PAGADA', '2026-07-07 08:37:00.000000', 1800.00, 2),
(38, 'PAGADA', '2026-07-07 08:38:00.000000', 6500.00, 3),
(39, 'PAGADA', '2026-07-07 08:39:00.000000', 2400.00, 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`id_detalleventa`),
  ADD KEY `FKan6gswye1edvp2ap3mt4rgbkr` (`producto_id_producto`),
  ADD KEY `FKfap6vt5yklivbaxk3yw9n2kiq` (`venta_id_venta`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id_producto`),
  ADD UNIQUE KEY `UKsat8uh55mbbue8o6g5s6vb23j` (`nombre_producto`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`id_sucursal`),
  ADD UNIQUE KEY `UKse7pcrmnyxritjvx8wka0yaon` (`direccion_sucursal`),
  ADD UNIQUE KEY `UKi96lbmqlyak3m3jo48de6m7rt` (`nombre_sucursal`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `FK3u2g9mwucc0h13a4o2le5cfmf` (`sucursal_id_sucursal`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalleventa` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id_producto` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  MODIFY `id_sucursal` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `id_venta` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `FKan6gswye1edvp2ap3mt4rgbkr` FOREIGN KEY (`producto_id_producto`) REFERENCES `producto` (`id_producto`),
  ADD CONSTRAINT `FKfap6vt5yklivbaxk3yw9n2kiq` FOREIGN KEY (`venta_id_venta`) REFERENCES `venta` (`id_venta`);

--
-- Filtros para la tabla `venta`
--
ALTER TABLE `venta`
  ADD CONSTRAINT `FK3u2g9mwucc0h13a4o2le5cfmf` FOREIGN KEY (`sucursal_id_sucursal`) REFERENCES `sucursal` (`id_sucursal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
