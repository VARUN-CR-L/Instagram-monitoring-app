# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render




from django.http import JsonResponse
import instaloader
import socketio


sio = socketio.Server()


def track_instagram_profile(request, username):
    L = instaloader.Instaloader()

    try:
        
        profile = instaloader.Profile.from_username(L.context, username)

        profile_data = {
            'username': profile.username,
            'full_name': profile.full_name,
            'biography': profile.biography,
            'follower_count': profile.followers,
            'following_count': profile.followees,
            'profile_pic_url': profile.profile_pic_url,
            'is_private': profile.is_private,
            'is_verified': profile.is_verified,
            'posts': [],
        }

        
        if not profile.is_private:
            for post in profile.get_posts():
                profile_data['posts'].append({
                    'caption': post.caption,
                    'likes': post.likes,
                    'comments': post.comments,
                    'timestamp': post.date.strftime('%Y-%m-%d %H:%M:%S'),
                    'image_url': post.url,
                })
        
        return JsonResponse(profile_data)
    
    except instaloader.exceptions.ProfileNotExistsException:
        return JsonResponse({'error': 'Profile not found'}, status=404)
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)
        


