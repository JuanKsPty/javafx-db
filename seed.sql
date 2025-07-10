IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'BD_NUEVAJAVA')
    BEGIN
        CREATE DATABASE BD_NUEVAJAVA;
        PRINT 'Database BD_NUEVAJAVA created successfully';
    END
ELSE
    BEGIN
        PRINT 'Database BD_NUEVAJAVA already exists';
    END
GO

USE BD_NUEVAJAVA;
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Customers]') AND type in (N'U'))
    BEGIN
        CREATE TABLE [dbo].[Customers]
        (
            CustomerID   NVARCHAR(5)  NOT NULL PRIMARY KEY,
            CompanyName  NVARCHAR(40) NOT NULL,
            ContactName  NVARCHAR(30),
            ContactTitle NVARCHAR(30),
            Address      NVARCHAR(60),
            City         NVARCHAR(15),
            Region       NVARCHAR(15),
            PostalCode   NVARCHAR(10),
            Country      NVARCHAR(15),
            Phone        NVARCHAR(24)
        );
        PRINT 'Table Customers created successfully';
    END
ELSE
    BEGIN
        PRINT 'Table Customers already exists';
    END
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[users]') AND type in (N'U'))
    BEGIN
        CREATE TABLE [dbo].[users]
        (
            id       INT IDENTITY(1,1) PRIMARY KEY,
            name     VARCHAR(100) NOT NULL,
            email    VARCHAR(100) NOT NULL,
            password VARCHAR(100) NOT NULL
        );
        PRINT 'Table users created successfully';
    END
ELSE
    BEGIN
        PRINT 'Table users already exists';
    END
GO


DELETE FROM [dbo].[Customers];
DELETE FROM [dbo].[users];
GO

DBCC CHECKIDENT ('[dbo].[users]', RESEED, 0);
GO

INSERT INTO [dbo].[Customers] (CustomerID, CompanyName, ContactName, ContactTitle, Address, City, Region, PostalCode, Country, Phone) VALUES
                                                                                                                                          (N'ALFKI', N'Alfreds Futterkiste', N'Maria', N'Sales Representative', N'Obere Str. 57', N'Berlin', N'Metro', N'12209', N'Germany', N'030-0074321'),
                                                                                                                                          (N'ANATR', N'Ana Trujillo Emparedados y helados', N'Ana Trujillo', N'Owner', N'Avda. de la Constitución 2222', N'México D.F.', NULL, N'05021', N'Mexico', N'(5) 555-4729'),
                                                                                                                                          (N'ANTON', N'Antonio Moreno Taquería', N'Antonio Moreno', N'Owner', N'Mataderos  2312', N'México D.F.', NULL, N'05023', N'Mexico', N'(5) 555-3932'),
                                                                                                                                          (N'AROUT', N'Around the Horn', N'Thomas Hardy', N'Sales Representative', N'120 Hanover Sq.', N'London', NULL, N'WA1 1DP', N'UK', N'(171) 555-7788'),
                                                                                                                                          (N'BERGS', N'Berglunds snabbköp', N'Christina Berglund', N'Order Administrator', N'Berguvsvägen  8', N'Luleå', NULL, N'S-958 22', N'Sweden', N'0921-12 34 65'),
                                                                                                                                          (N'BLAUS', N'Blauer See Delikatessen', N'Hanna Moos', N'Sales Representative', N'Forsterstr. 57', N'Mannheim', NULL, N'68306', N'Germany', N'0621-08460'),
                                                                                                                                          (N'BLONP', N'Blondesddsl père et fils', N'Frédérique Citeaux', N'Marketing Manager', N'24, place Kléber', N'Strasbourg', NULL, N'67000', N'France', N'88.60.15.31'),
                                                                                                                                          (N'BOLID', N'Bólido Comidas preparadas', N'Martín Sommer', N'Owner', N'C/ Araquil, 67', N'Madrid', NULL, N'28023', N'Spain', N'(91) 555 22 82'),
                                                                                                                                          (N'BONAP', N'Bon app''', N'Laurence Lebihan', N'Owner', N'12, rue des Bouchers', N'Marseille', NULL, N'13008', N'France', N'91.24.45.40'),
                                                                                                                                          (N'BOTTM', N'Bottom-Dollar Markets', N'Elizabeth Lincoln', N'Accounting Manager', N'23 Tsawassen Blvd.', N'Chiriqui- Meto', N'KL02', N'T2F 8M4', N'Panamá', N'(604) 555-4729'),
                                                                                                                                          (N'BSBEV', N'B''s Beverages', N'Victoria Ashworth', N'Sales Representative', N'Fauntleroy Circus', N'London', NULL, N'EC2 5NT', N'UK', N'(171) 555-1212'),
                                                                                                                                          (N'CACTU', N'Cactus Comidas para llevar', N'Patricio Simpson', N'Sales Agent', N'Cerrito 333', N'Buenos Aires', NULL, N'1010', N'Argentina', N'(1) 135-5555'),
                                                                                                                                          (N'CENTC', N'Centro comercial Moctezuma', N'Francisco Chang', N'Marketing Manager', N'Sierras de Granada 9993', N'México D.F.', NULL, N'05022', N'Mexico', N'(5) 555-3392'),
                                                                                                                                          (N'CHOPS', N'Chop-suey Chinese', N'Yang Wang', N'Owner', N'Hauptstr. 29', N'Bern', NULL, N'3012', N'Switzerland', N'0452-076545'),
                                                                                                                                          (N'COMMI', N'Comércio Mineiro', N'Pedro Afonso', N'Sales Associate', N'Av. dos Lusíadas, 23', N'Sao Paulo', N'SP', N'05432-043', N'Brazil', N'(11) 555-7647'),
                                                                                                                                          (N'CONSH', N'Consolidated Holdings', N'Elizabeth Brown', N'Sales Representative', N'Berkeley Gardens 12  Brewery', N'London', NULL, N'WX1 6LT', N'UK', N'(171) 555-2282'),
                                                                                                                                          (N'DRACD', N'Drachenblut Delikatessen', N'Sven Ottlieb', N'Order Administrator', N'Walserweg 21', N'Aachen', NULL, N'52066', N'Germany', N'0241-039123'),
                                                                                                                                          (N'DUMON', N'Du monde entier', N'Janine Labrune', N'Owner', N'67, rue des Cinquante Otages', N'Nantes', NULL, N'44000', N'France', N'40.67.88.88'),
                                                                                                                                          (N'EASTC', N'Eastern Connection', N'Ann Devon', N'Sales Agent', N'35 King George', N'London', NULL, N'WX3 6FW', N'UK', N'(171) 555-0297'),
                                                                                                                                          (N'ERNSH', N'Ernst Handel', N'Roland Mendel', N'Sales Manager', N'Kirchgasse 6', N'Graz', NULL, N'8010', N'Austria', N'7675-3425'),
                                                                                                                                          (N'KKAi', N'UTP', N'Juan', N'Conador', N'Condado del Rey', N'Colon', N'Sur', N'6666', N'Sabanitas', N'123-12515'),
                                                                                                                                          (N'VGT', N'Edupan', N'Ricardo ruiz', N'Boss', N'Santiago', N'Santiago', N'Veraguas', N'1932', N'Panama', N'132-5211');
GO

PRINT '';
PRINT '============================================';
PRINT 'Database setup completed successfully!';
PRINT '============================================';
PRINT 'Database: BD_NUEVAJAVA';
PRINT 'Tables created: Customers, users';
SELECT 'Customers: ' + CAST(COUNT(*) AS VARCHAR(10)) + ' records' AS 'Data Summary' FROM [dbo].[Customers]
UNION ALL
SELECT 'Users: ' + CAST(COUNT(*) AS VARCHAR(10)) + ' records' FROM [dbo].[users];
GO