gen-java:
	protoc proto/*.proto --java_out=src/main/java
gen-grpc:
	protoc grpc/*.proto \
        --java_out=src/main/java \
        --grpc-java_out=src/main/java
gen-python:
	protoc proto/*.proto --python_out=generated
publish-python:
	rm -rf build dist *.egg-info
	python setup.py sdist bdist_wheel
	twine upload dist/*