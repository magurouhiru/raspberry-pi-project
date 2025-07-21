import os
from dataclasses import asdict

from bottle import Bottle, route, HTTPResponse, error, HTTPError

from device.device_info import provide_device_info

__version__ = "0.2.0"

app = Bottle()

APP_ENV = os.getenv("APP_ENV", "prod").lower()
MOCK_MODE = os.getenv("MOCK_MODE", "false").lower() == "true"
deviceInfo = provide_device_info(MOCK_MODE)

with app:
    @route('/health', method='GET')
    def health():
        return "health:OK"


    @route('/ready', method='GET')
    def ready():
        return "ready:OK"


    @route('/device/app/env', method='GET')
    def ready():
        return {"APP_ENV": APP_ENV, "MOCK_MODE": MOCK_MODE}


    @route('/device/temp', method='GET')
    def temp():
        try:
            tmp = deviceInfo.get_temp()
            return tmp.__dict__
        except Exception as e:
            return HTTPError(status=500, exception=e)


    @route('/device/freq', method='GET')
    def freq():
        try:
            tmp = deviceInfo.get_freq()
            return tmp.__dict__
        except Exception as e:
            return HTTPError(status=500, exception=e)


    @route('/device/cpu', method='GET')
    def cpu():
        try:
            tmp = deviceInfo.get_cpu()
            return asdict(tmp)
        except Exception as e:
            return HTTPError(status=500, exception=e)


    @route('/device/mem', method='GET')
    def mem():
        try:
            tmp = deviceInfo.get_mem()
            return tmp.__dict__
        except Exception as e:
            return HTTPError(status=500, exception=e)


    @error(404)
    def error404(e: HTTPError):
        body = str(e.body)
        return HTTPResponse(body=body, status=404)


    @error(500)
    def error500(e: HTTPError):
        if e.exception is None:
            body = str(e.body)
        else:
            body = 'exception: ' + e.exception.__class__.__name__ + "\nmessage: " + str(e.exception)
        return HTTPResponse(body=body, status=500)
