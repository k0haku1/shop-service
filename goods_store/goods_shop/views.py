from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from drf_yasg.utils import swagger_auto_schema
from .service import GoodsService


service = GoodsService()
@swagger_auto_schema(method='get', operation_summary="Получить все товары", operation_id="get_all_goods")
@api_view(['GET'])
def goods_list_api(request):
    goods_data = service.get_all_goods()
    return Response(goods_data)

@swagger_auto_schema(method='post', operation_summary="Создать новый товар", operation_id="create_good")
@api_view(['POST'])
def goods_create_api(request):
    data, response_status = service.create_good(request.data)
    return Response(data, status=response_status)

@swagger_auto_schema(method='delete', operation_summary="Удалить товар по ID", operation_id="delete_good")
@api_view(['DELETE'])
def good_delete_api(request, id):
    success = service.delete_good(id)
    if success:
        return Response({"message": "Good deleted successfully"}, status=status.HTTP_204_NO_CONTENT)
    else:
        return Response({"error": "Good not found"}, status=status.HTTP_404_NOT_FOUND)

@swagger_auto_schema(method='put', operation_summary="Обновить товар по ID", operation_id="update_good")
@api_view(['PUT'])
def goods_update_api(request, id):
    updated_data, errors = service.update_good(id, request.data)
    if updated_data is not None:
        return Response(updated_data, status=status.HTTP_200_OK)
    elif errors:
        return Response(errors, status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response({"error": "Good not found"}, status=status.HTTP_404_NOT_FOUND)


@swagger_auto_schema(method='get', operation_summary="Получить товар по ID", operation_id="get_good_by_id")
@api_view(['GET'])
def goods_detail_api(request, id):
    good = service.get_good(id)
    if good is not None:
        return Response(good, status=status.HTTP_200_OK)
    return Response({"error": "Good not found"}, status=status.HTTP_404_NOT_FOUND)