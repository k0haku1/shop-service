from django.urls import path
from . import views

urlpatterns = [
    path('api/goods_shop/', views.goods_list_api, name='goods_list_api'),
    path('api/goods_shop/<int:id>/', views.goods_detail_api, name='goods_detail_api'),
    path('api/goods_shop/update/<int:id>/', views.goods_update_api, name='goods_update_api'),
    path('api/goods_shop/delete/<int:id>/', views.good_delete_api, name='goods_delete_api'),
    path('api/goods_shop/create/', views.goods_create_api, name='goods_create_api'),
]