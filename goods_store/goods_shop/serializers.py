from rest_framework import serializers
from .models import goodsTable


class GoodsTableSerializer(serializers.ModelSerializer):
    class Meta:
        model = goodsTable
        fields = '__all__'

    product_price = serializers.DecimalField(max_digits=10, decimal_places=2, coerce_to_string=False, default=0.00)