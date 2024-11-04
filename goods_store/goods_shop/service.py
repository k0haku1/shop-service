from .models import goodsTable
from .serializers import GoodsTableSerializer
from rest_framework import status
from rest_framework.response import Response


class GoodsService:
    @staticmethod
    def get_all_goods():
        serializer = GoodsTableSerializer(goodsTable.objects.all(), many=True)
        return serializer.data

    @staticmethod
    def create_good(data):
        serializer = GoodsTableSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return serializer.data, status.HTTP_201_CREATED
        return serializer.errors, status.HTTP_400_BAD_REQUEST

    @staticmethod
    def get_good(id):
        try:
            good = goodsTable.objects.get(id=id)
            serializer = GoodsTableSerializer(good)
            return serializer.data
        except goodsTable.DoesNotExist:
            return None

    @staticmethod
    def update_good(id, data):
        try:
            good = goodsTable.objects.get(id=id)
            serializer = GoodsTableSerializer(good, data=data)
            if serializer.is_valid():
                serializer.save()
                return serializer.data, None
            return None, serializer.errors
        except goodsTable.DoesNotExist:
            return None, {"error": "Good not found"}

    @staticmethod
    def delete_good(id):
        try:
            good = goodsTable.objects.get(id=id)
            good.delete()
            return True
        except goodsTable.DoesNotExist:
            return None, {"error": "Good not found"}