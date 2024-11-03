from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from .models import goodsTable
from .serializers import GoodsTableSerializer


@api_view(['GET', 'POST'])
def goods_list_api(request):
    if request.method == 'GET':
        goods = goodsTable.objects.all()
        serializer = GoodsTableSerializer(goods, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = GoodsTableSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


@api_view(['GET', 'PUT', 'PATCH', 'DELETE'])
def goods_detail_api(request, id):
    try:
        good = goodsTable.objects.get(id=id)
    except goodsTable.DoesNotExist:
        return Response(status=status.HTTP_404_NOT_FOUND)

    if request.method == 'GET':
        serializer = GoodsTableSerializer(good)
        return Response(serializer.data)

    elif request.method == 'PUT':
        serializer = GoodsTableSerializer(good, data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'DELETE':
        good.delete()
        return Response(status=status.HTTP_204_NO_CONTENT)
