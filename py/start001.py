import MySQLdb as mysql

mydb = mysql.connect(host="localhost",user="jzuo",passwd="jzuo",db="menu")

cursor = mydb.cursor()

count = cursor.execute('select * from fish')

results = cursor.fetchall()

print count, results

for record in results:
  print record[0], "-->", record[1], " @", record[2], "each"


mydb.close()