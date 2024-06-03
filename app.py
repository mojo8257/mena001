import logging
from flask import Flask, render_template, request

app = Flask(__name__)

app.logger.setLevel(logging.DEBUG)

file_handler = logging.FileHandler('flask_app.log')
file_handler.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
file_handler.setFormatter(formatter)

app.logger.addHandler(file_handler)

@app.route('/')
def home():
    return render_template('index.html')


@app.route('/greet', methods=['POST'])
def greet():
    name = request.form['name']
    return f'Hello, {name}!'


if __name__ == '__main__':
    app.run(debug=True)
