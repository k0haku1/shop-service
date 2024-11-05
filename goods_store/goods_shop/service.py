from .models import goodsTable
from .serializers import GoodsTableSerializer
from rest_framework import status


class GoodsService:
    def get_all_goods(self):
        serializer = GoodsTableSerializer(goodsTable.objects.all(), many=True)
        return serializer.data


    def create_good(self, data):
        serializer = GoodsTableSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return serializer.data, status.HTTP_201_CREATED
        return serializer.errors, status.HTTP_400_BAD_REQUEST


    def get_good(self, id):
        try:
            good = goodsTable.objects.get(id=id)
            serializer = GoodsTableSerializer(good)
            return serializer.data
        except goodsTable.DoesNotExist:
            return None


    def update_good(self, id, data):
        try:
            good = goodsTable.objects.get(id=id)
            serializer = GoodsTableSerializer(good, data=data)
            if serializer.is_valid():
                serializer.save()
                return serializer.data, None
            return None, serializer.errors
        except goodsTable.DoesNotExist:
            return None, {"error": "Good not found"}


    def delete_good(self, id):
        try:
            good = goodsTable.objects.get(id=id)
            good.delete()
            return True
        except goodsTable.DoesNotExist:
            return None, {"error": "Good not found"}