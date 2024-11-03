from django.db import models

class goodsTable(models.Model):
    id = models.AutoField(primary_key=True)
    product_name = models.CharField(max_length=100)
    product_description = models.TextField(max_length=100)
    product_category = models.CharField(max_length=100)
    product_price = models.DecimalField(max_digits=5, decimal_places=2)
    product_quantity = models.IntegerField(default=0)
    create_date = models.DateField(auto_now_add=True)

    class Meta:
        db_table = 'goods'
        managed = False