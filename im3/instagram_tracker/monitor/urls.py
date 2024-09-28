from django.urls import path
from . import views

urlpatterns = [
    path('track/<str:username>/', views.track_instagram_profile, name='track_instagram_profile'),
]

