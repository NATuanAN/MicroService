package main

import (
	"encoding/json"
	"log"
	"math/rand"
	"strconv"

	amqp "github.com/rabbitmq/amqp091-go"
)

type LoginRequest struct {
	Username string `json:"username"`
	Password string `json:"password"`
}

func main() {
	conn, _ := amqp.Dial("amqp://guest:guest@localhost:5672/")
	defer conn.Close()

	ch, _ := conn.Channel()
	defer ch.Close()

	// Queue tạm để nhận response
	replyQueue, _ := ch.QueueDeclare(
		"",    // tên rỗng → RabbitMQ tự tạo queue random
		false, // durable
		true,  // auto-delete
		true,  // exclusive
		false, // no-wait
		nil,
	)

	msgs, _ := ch.Consume(
		replyQueue.Name, "", true, true, false, false, nil,
	)

	corrId := strconv.Itoa(rand.Int())

	body, _ := json.Marshal(LoginRequest{
		Username: "tuan",
		Password: "123456",
	})

	// Gửi login request đến auth-queue
	err := ch.Publish(
		"",
		"auth-queue",
		false,
		false,
		amqp.Publishing{
			ContentType:   "application/json",
			CorrelationId: corrId,
			ReplyTo:       replyQueue.Name,
			Body:          body,
		},
	)
	if err != nil {
		log.Fatalf("Lỗi gửi: %v", err)
	}

	// Chờ kết quả
	for d := range msgs {
		if d.CorrelationId == corrId {
			log.Printf("Kết quả login: %s", d.Body)
			break
		}
	}
}
