from bottle import run, route


@route('/health', method='GET')
def health():
    return "health:OK"


@route('/ready', method='GET')
def ready():
    return "ready:OK"


if __name__ == '__main__':
    run(host='0.0.0.0', port=8080, debug=True)
