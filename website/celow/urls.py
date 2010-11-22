from django.conf.urls.defaults import *
from django.views.generic.simple import *
from django.conf import settings

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    #default index page
    (r'^$', direct_to_template, {'template': 'index.html'}),
    (r'^help/$', direct_to_template, {'template': 'help-applet.html'}),
    (r'^help/mobile/$', direct_to_template, {'template': 'help-android.html'}),
    (r'^media/(?P<path>.*)$', 'django.views.static.serve', {'document_root': settings.MEDIA_ROOT}),
    # Example:
    # (r'^website/', include('website.foo.urls')),

    # Uncomment the admin/doc line below and add 'django.contrib.admindocs' 
    # to INSTALLED_APPS to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # (r'^admin/', include(admin.site.urls)),
)
