import os
from dataclasses import asdict

from bottle import Bottle, route, abort

from app.device_info import provide_device_info

__version__ = "0.1.0"

app = Bottle()

MOCK_MODE = os.getenv("MOCK_MODE", "false").lower() == "true"
deviceInfo = provide_device_info(True)

with app:
    @route('/health', method='GET')
    def health():
        return "health:OK"


    @route('/ready', method='GET')
    def ready():
        return "ready:OK"


    @route('/device/temp', method='GET')
    def temp():
        try:
            tmp = deviceInfo.get_temp()
            return tmp.__dict__
        except Exception as e:
            return abort(500, e)


    @route('/device/freq', method='GET')
    def freq():
        try:
            tmp = deviceInfo.get_freq()
            return tmp.__dict__
        except Exception as e:
            return abort(500, e)


    @route('/device/cpu', method='GET')
    def cpu():
        try:
            tmp = deviceInfo.get_cpu()
            return asdict(tmp)
        except Exception as e:
            return abort(500, e)


    @route('/device/mem', method='GET')
    def mem():
        try:
            tmp = deviceInfo.get_mem()
            return tmp.__dict__
        except Exception as e:
            return abort(500, e)
