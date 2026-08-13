$mysql = 'C:\Program Files\MySQL\MySQL Server 9.6\bin\mysql.exe'
$sql = 'CREATE DATABASE IF NOT EXISTS ejercicio_practico2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; SHOW DATABASES;'
& $mysql -u root -pVale10 --execute=$sql 2>&1
