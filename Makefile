
start:
	docker-compose -f docker-compose.yaml down || true
	docker-compose -f docker-compose.yaml up --build | tee docker-compose.log

stop:
	docker-compose -f docker-compose.yaml down
