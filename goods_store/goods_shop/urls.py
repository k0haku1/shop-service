from django.urls import path
from . import views

urlpatterns = [
    path('api/goods/', views.goods_list_api, name='goods_list_api'),
    path('api/goods/<int:id>/', views.goods_detail_api, name='goods-detail'),
]