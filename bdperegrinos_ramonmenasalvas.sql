-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-11-2024 a las 04:49:48
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
-- Base de datos: `bdperegrinos_ramonmenasalvas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carnets`
--

CREATE TABLE `carnets` (
  `id` bigint(20) NOT NULL,
  `idParadaExpedido` bigint(20) NOT NULL,
  `fechaExpedido` date NOT NULL DEFAULT current_timestamp(),
  `distancia` decimal(10,0) NOT NULL DEFAULT 0,
  `numeroVips` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carnets`
--

INSERT INTO `carnets` (`id`, `idParadaExpedido`, `fechaExpedido`, `distancia`, `numeroVips`) VALUES
(13, 12, '2024-11-04', 0, 0),
(14, 16, '2024-11-04', 10, 1),
(15, 18, '2024-11-04', 0, 0),
(16, 17, '2024-11-04', 25, 3),
(17, 13, '2024-11-04', 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estancias`
--

CREATE TABLE `estancias` (
  `id` bigint(20) NOT NULL,
  `idPeregrino` bigint(20) NOT NULL,
  `idParada` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `vip` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estancias`
--

INSERT INTO `estancias` (`id`, `idPeregrino`, `idParada`, `fecha`, `vip`) VALUES
(3, 14, 16, '2024-11-04', 1),
(4, 14, 12, '2024-11-05', 1),
(5, 14, 18, '2024-11-06', 0),
(6, 14, 17, '2024-11-07', 0),
(7, 14, 13, '2024-11-08', 1),
(8, 12, 16, '2024-11-04', 0),
(9, 12, 12, '2024-11-05', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paradas`
--

CREATE TABLE `paradas` (
  `id` bigint(20) NOT NULL,
  `idResponsable` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `region` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paradas`
--

INSERT INTO `paradas` (`id`, `idResponsable`, `nombre`, `region`) VALUES
(12, 1, 'gijon', 'a'),
(13, 2, 'oviedo', 'a'),
(16, 24, 'candas', 'a'),
(17, 23, 'mieres', 'a'),
(18, 25, 'lugones', 'a');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peregrinos`
--

CREATE TABLE `peregrinos` (
  `id` bigint(20) NOT NULL,
  `idUsuario` bigint(20) NOT NULL,
  `idCarnet` bigint(20) NOT NULL,
  `nombreCompleto` varchar(50) NOT NULL,
  `nacionalidad` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `peregrinos`
--

INSERT INTO `peregrinos` (`id`, `idUsuario`, `idCarnet`, `nombreCompleto`, `nacionalidad`) VALUES
(11, 26, 13, 'noel el apuntador', 'España'),
(12, 27, 14, 'carla la fiestera', 'España'),
(13, 28, 15, 'hector el trabajador', 'España'),
(14, 29, 16, 'alberto el caotico', 'España'),
(15, 30, 17, 'david el futbolista', 'España');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `perfil` enum('peregrino','parada') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `password`, `perfil`) VALUES
(1, 'ramon', '1234', 'parada'),
(2, 'javi', '1234', 'parada'),
(23, 'dani', '1234', 'parada'),
(24, 'lucia', '1234', 'parada'),
(25, 'sophie', '1234', 'parada'),
(26, 'noel', '1234', 'peregrino'),
(27, 'carla', '2345', 'peregrino'),
(28, 'hector', '2345', 'peregrino'),
(29, 'alberto', '4444', 'peregrino'),
(30, 'david', '2222', 'peregrino');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `visitadas`
--

CREATE TABLE `visitadas` (
  `idPeregrino` bigint(11) NOT NULL,
  `idParada` bigint(11) NOT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `visitadas`
--

INSERT INTO `visitadas` (`idPeregrino`, `idParada`, `fecha`) VALUES
(12, 12, '2024-11-05'),
(12, 13, '2024-11-08'),
(12, 16, '2024-11-04'),
(12, 17, '2024-11-07'),
(12, 18, '2024-11-06'),
(13, 18, '2024-11-04'),
(14, 12, '2024-11-05'),
(14, 13, '2024-11-08'),
(14, 16, '2024-11-04'),
(14, 17, '2024-11-07'),
(14, 18, '2024-11-06'),
(15, 17, '2024-11-04'),
(15, 18, '2024-11-05');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carnets`
--
ALTER TABLE `carnets`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idParadaExpedido` (`idParadaExpedido`) USING BTREE;

--
-- Indices de la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idPeregrino` (`idPeregrino`) USING BTREE,
  ADD KEY `idParada` (`idParada`) USING BTREE;

--
-- Indices de la tabla `paradas`
--
ALTER TABLE `paradas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idResponsable` (`idResponsable`);

--
-- Indices de la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idUsuario` (`idUsuario`),
  ADD UNIQUE KEY `idCarnet` (`idCarnet`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `visitadas`
--
ALTER TABLE `visitadas`
  ADD PRIMARY KEY (`idPeregrino`,`idParada`,`fecha`),
  ADD KEY `idPeregrino` (`idPeregrino`) USING BTREE,
  ADD KEY `fk_visitadasParadas` (`idParada`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carnets`
--
ALTER TABLE `carnets`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `estancias`
--
ALTER TABLE `estancias`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `paradas`
--
ALTER TABLE `paradas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carnets`
--
ALTER TABLE `carnets`
  ADD CONSTRAINT `fk_carnetsParadas` FOREIGN KEY (`idParadaExpedido`) REFERENCES `paradas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `estancias`
--
ALTER TABLE `estancias`
  ADD CONSTRAINT `fk_ParadasEstancias` FOREIGN KEY (`idParada`) REFERENCES `paradas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_PeregrinoEstancias` FOREIGN KEY (`idPeregrino`) REFERENCES `peregrinos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `paradas`
--
ALTER TABLE `paradas`
  ADD CONSTRAINT `fk_ParadasUsuarios` FOREIGN KEY (`idResponsable`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `peregrinos`
--
ALTER TABLE `peregrinos`
  ADD CONSTRAINT `fk_carnetPeregrinos` FOREIGN KEY (`idCarnet`) REFERENCES `carnets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_peregrinoUsuarios` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `visitadas`
--
ALTER TABLE `visitadas`
  ADD CONSTRAINT `fk_visitadasParadas` FOREIGN KEY (`idParada`) REFERENCES `paradas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_visitadasPeregrino` FOREIGN KEY (`idPeregrino`) REFERENCES `peregrinos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
