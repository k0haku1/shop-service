from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

from .models import goodsTable
from .serializers import GoodsTableSerializer
from .service import GoodsService


@api_view(['GET'])
def goods_list_api(request):
    goods_data = GoodsService.get_all_goods()
    return Response(goods_data)


@api_view(['POST'])
def goods_create_api(request):
    data, response_status = GoodsService.create_good(request.data)
    return Response(data, status=response_status)


@api_view(['DELETE'])
def good_delete_api(request, id):
    success = GoodsService.delete_good(id)
    if success:
        return Response({"message": "Good deleted successfully"}, status=status.HTTP_204_NO_CONTENT)
    else:
        return Response({"error": "Good not found"}, status=status.HTTP_404_NOT_FOUND)


@api_view(['PUT'])
def goods_update_api(request, id):
    updated_data, errors = GoodsService.update_good(id, request.data)
    if updated_data is not None:
        return Response(updated_data, status=status.HTTP_200_OK)
    elif errors:
        return Response(errors, status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response({"error": "Good not found"}, status=status.HTTP_404_NOT_FOUND)


@api_view(['GET'])
def goods_detail_api(request, id):
    good = GoodsService.get_good(id)
    if good is not None:
        return Response(good, status=status.HTTP_200_OK)
    return Response({"error": "Good not found"}, status=status.HTTP_404_NOT_FOUND)