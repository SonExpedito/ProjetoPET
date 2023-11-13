-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 13/11/2023 às 23:01
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `projetopet`
--
create database `projetopet`;
use `projetopet`;
-- --------------------------------------------------------

--
-- Estrutura para tabela `administrador`
--

CREATE TABLE `administrador` (
  `Usuario` varchar(20) NOT NULL,
  `Senha` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `administrador`
--

INSERT INTO `administrador` (`Usuario`, `Senha`) VALUES
('Alex', '123'),
('Edna', '456');

-- --------------------------------------------------------

--
-- Estrutura para tabela `animais`
--

CREATE TABLE `animais` (
  `Num_Registro` int(11) NOT NULL,
  `Nome_Pet` varchar(50) NOT NULL,
  `Especie` varchar(35) NOT NULL,
  `Raca` varchar(25) NOT NULL,
  `Cor_Predominante` varchar(25) NOT NULL,
  `Data_Nasc` date NOT NULL,
  `Sexo` varchar(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `animais`
--

INSERT INTO `animais` (`Num_Registro`, `Nome_Pet`, `Especie`, `Raca`, `Cor_Predominante`, `Data_Nasc`, `Sexo`) VALUES
(1, 'Gojo', 'Cachorro', 'Husky Siberiano', 'Branco', '2023-11-01', 'Macho'),
(2, 'Xai', 'Gata', 'Vira Lata', 'Branca', '2020-10-20', 'Fêmea'),
(3, 'Rafael', 'Tucano', 'Tucano-toco', 'Preto', '2019-10-02', 'Macho'),
(4, 'Bella', 'Cachorro', 'Vira Lata', 'Morena', '2022-05-10', 'Fêmea'),
(5, 'Felix', 'Primata', 'Mico-Leão Dourado', 'Vermelho', '2006-12-20', 'Macho');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `animais`
--
ALTER TABLE `animais`
  ADD PRIMARY KEY (`Num_Registro`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `animais`
--
ALTER TABLE `animais`
  MODIFY `Num_Registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
