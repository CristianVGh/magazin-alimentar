-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 17, 2019 at 06:49 AM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 5.6.39

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `magazin_alimentar`
--

-- --------------------------------------------------------

--
-- Table structure for table `angajat`
--

CREATE TABLE `angajat` (
  `coda` int(11) NOT NULL,
  `nume` varchar(40) DEFAULT NULL,
  `rol` varchar(20) DEFAULT NULL,
  `telefon` varchar(15) DEFAULT NULL,
  `adresa` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `angajat`
--

INSERT INTO `angajat` (`coda`, `nume`, `rol`, `telefon`, `adresa`) VALUES
(1, 'Danica Mihaela', 'vanzator', '0776399123', 'Str. Becului nr.12, Bucuresti'),
(2, 'Spanu Cosmin', 'vanzator', '0773123554', 'Str. Basmului nr.54, Bucuresti'),
(3, 'Miron Georgiana', 'vanzator', '0721294832', 'Str. Bacului nr.144, Bucuresti'),
(4, 'Papadolop Stefan', 'manager', '0741239423', 'Str. Soferiei nr.23, Bucuresti');

-- --------------------------------------------------------

--
-- Table structure for table `bon_fiscal`
--

CREATE TABLE `bon_fiscal` (
  `codb` int(11) NOT NULL,
  `pret_total` int(11) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `coda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bon_fiscal`
--

INSERT INTO `bon_fiscal` (`codb`, `pret_total`, `data`, `coda`) VALUES
(1, 13, '2019-01-17', 3),
(2, 2019, '2019-01-17', 1),
(3, 2019, '2019-01-17', 1),
(4, 2019, '2019-01-17', 1),
(5, 98, '2019-01-17', 1);

-- --------------------------------------------------------

--
-- Table structure for table `categorie`
--

CREATE TABLE `categorie` (
  `codc` int(11) NOT NULL,
  `denumire` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `categorie`
--

INSERT INTO `categorie` (`codc`, `denumire`) VALUES
(1, 'Legume'),
(2, 'Fructe'),
(3, 'Lactate'),
(4, 'Carne'),
(5, 'Semipreparate'),
(6, 'Produse de panificatie');

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `codi` int(11) NOT NULL,
  `denumire` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`codi`, `denumire`) VALUES
(1, 'sare'),
(2, 'zahar'),
(3, 'faina'),
(4, 'oua'),
(5, 'lapte'),
(6, 'carne de pui'),
(7, 'carne de porc'),
(8, 'carne de vita'),
(9, 'carne de oaie');

-- --------------------------------------------------------

--
-- Table structure for table `ingredient_pe_produs`
--

CREATE TABLE `ingredient_pe_produs` (
  `codp` int(11) DEFAULT NULL,
  `codi` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ingredient_pe_produs`
--

INSERT INTO `ingredient_pe_produs` (`codp`, `codi`) VALUES
(8, 7),
(8, 8),
(9, 7),
(13, 3),
(13, 1),
(10, 6);

-- --------------------------------------------------------

--
-- Table structure for table `producator`
--

CREATE TABLE `producator` (
  `codpr` int(11) NOT NULL,
  `nume` varchar(40) DEFAULT NULL,
  `telefon` varchar(15) DEFAULT NULL,
  `tara` varchar(20) DEFAULT NULL,
  `adresa` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `producator`
--

INSERT INTO `producator` (`codpr`, `nume`, `telefon`, `tara`, `adresa`) VALUES
(1, 'Marex', '022344231', 'Romania', 'str. Apollo nr. 54, Braila'),
(2, 'Agrimondo', '0332452781', 'Romania', 'str. Abatorului nr. 32, Bucuresti'),
(3, 'TARSIM', '026552412', 'Turcia', 'str. Sri Lanka nr. 12, Alihabi'),
(4, 'Caroli', '0743322121', 'Romania', 'str. Spicului nr. 5, Bucuresti'),
(5, 'Cristim', '073328730', 'Romania', 'str. Marului nr. 39, Timisoara'),
(6, 'Mangos', '022451221', 'Spania', 'str. Santa Maria nr. 21, Madrid'),
(7, 'Demopan', '0239987292', 'Romania', 'str. Turnului din Paris nr. 14, Braila'),
(8, 'Zabinox', '12312', 'tt', 'rtt');

-- --------------------------------------------------------

--
-- Table structure for table `produs`
--

CREATE TABLE `produs` (
  `codp` int(11) NOT NULL,
  `denumire` varchar(30) DEFAULT NULL,
  `pret` double DEFAULT NULL,
  `pret_achizitie` double DEFAULT NULL,
  `gramaj` varchar(8) DEFAULT NULL,
  `data_expirarii` date DEFAULT NULL,
  `data_fabricatiei` date DEFAULT NULL,
  `codpr` int(11) NOT NULL,
  `codc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produs`
--

INSERT INTO `produs` (`codp`, `denumire`, `pret`, `pret_achizitie`, `gramaj`, `data_expirarii`, `data_fabricatiei`, `codpr`, `codc`) VALUES
(1, 'Mere verzi', 5, 3, NULL, NULL, NULL, 2, 2),
(2, 'Mere verzi', 4.5, 3.1, NULL, NULL, NULL, 3, 2),
(3, 'Mere golden', 5.4, 4, NULL, NULL, NULL, 6, 2),
(4, 'Cartofi', 4, 3, NULL, NULL, NULL, 2, 1),
(5, 'Varza', 5.7, 4.6, NULL, NULL, NULL, 3, 1),
(6, 'Pere', 3, 2.3, NULL, NULL, NULL, 2, 2),
(7, 'Banane', 4, 3.3, NULL, NULL, NULL, 6, 2),
(8, 'Salam de vara', 8, 7, '600g', '2019-06-07', '2019-01-05', 5, 5),
(9, 'Salam taranesc', 8.3, 7.5, '700g', '2019-05-03', '2019-01-04', 1, 5),
(10, 'Parizer de pui', 10, 8.7, '600g', '2019-04-17', '2019-01-11', 4, 5),
(11, 'Carne de vita', 15, 12, NULL, NULL, NULL, 1, 4),
(12, 'Carne de oaie', 17, 14.4, NULL, NULL, NULL, 1, 4),
(13, 'Paine alba', 4, 0.5, '100g', '2019-01-20', '2019-01-16', 7, 6);

-- --------------------------------------------------------

--
-- Table structure for table `produs_pe_bon`
--

CREATE TABLE `produs_pe_bon` (
  `codp` int(11) NOT NULL,
  `codb` int(11) NOT NULL,
  `cantitate` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produs_pe_bon`
--

INSERT INTO `produs_pe_bon` (`codp`, `codb`, `cantitate`) VALUES
(2, 4, '4'),
(6, 4, '2'),
(10, 4, '2'),
(5, 4, '2'),
(7, 4, '2'),
(2, 5, '3'),
(12, 5, '3'),
(3, 5, '1'),
(9, 5, '2'),
(5, 5, '2');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `angajat`
--
ALTER TABLE `angajat`
  ADD PRIMARY KEY (`coda`);

--
-- Indexes for table `bon_fiscal`
--
ALTER TABLE `bon_fiscal`
  ADD PRIMARY KEY (`codb`),
  ADD KEY `coda` (`coda`);

--
-- Indexes for table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`codc`);

--
-- Indexes for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`codi`);

--
-- Indexes for table `ingredient_pe_produs`
--
ALTER TABLE `ingredient_pe_produs`
  ADD KEY `codp` (`codp`),
  ADD KEY `codi` (`codi`);

--
-- Indexes for table `producator`
--
ALTER TABLE `producator`
  ADD PRIMARY KEY (`codpr`);

--
-- Indexes for table `produs`
--
ALTER TABLE `produs`
  ADD PRIMARY KEY (`codp`),
  ADD KEY `codc` (`codc`),
  ADD KEY `codpr` (`codpr`);

--
-- Indexes for table `produs_pe_bon`
--
ALTER TABLE `produs_pe_bon`
  ADD KEY `codp` (`codp`),
  ADD KEY `codb` (`codb`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bon_fiscal`
--
ALTER TABLE `bon_fiscal`
  ADD CONSTRAINT `bon_fiscal_ibfk_1` FOREIGN KEY (`coda`) REFERENCES `angajat` (`coda`);

--
-- Constraints for table `ingredient_pe_produs`
--
ALTER TABLE `ingredient_pe_produs`
  ADD CONSTRAINT `ingredient_pe_produs_ibfk_1` FOREIGN KEY (`codp`) REFERENCES `produs` (`codp`),
  ADD CONSTRAINT `ingredient_pe_produs_ibfk_2` FOREIGN KEY (`codi`) REFERENCES `ingredient` (`codi`);

--
-- Constraints for table `produs`
--
ALTER TABLE `produs`
  ADD CONSTRAINT `produs_ibfk_1` FOREIGN KEY (`codc`) REFERENCES `categorie` (`codc`),
  ADD CONSTRAINT `produs_ibfk_2` FOREIGN KEY (`codpr`) REFERENCES `producator` (`codpr`);

--
-- Constraints for table `produs_pe_bon`
--
ALTER TABLE `produs_pe_bon`
  ADD CONSTRAINT `produs_pe_bon_ibfk_1` FOREIGN KEY (`codp`) REFERENCES `produs` (`codp`),
  ADD CONSTRAINT `produs_pe_bon_ibfk_2` FOREIGN KEY (`codb`) REFERENCES `bon_fiscal` (`codb`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
