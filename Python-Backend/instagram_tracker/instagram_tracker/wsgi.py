import os
from django.core.wsgi import get_wsgi_application
import socketio
from monitor.views import sio 

os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'instagram_tracker.settings')


django_app = get_wsgi_application()


application = socketio.WSGIApp(sio, django_app)

