package com.example.rentacar;

import com.example.rentacar.model.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RentACarApplication {
//14.
// === Система Прокат автомобілів. ===
// + Клієнт вибирає Автомобіль зі списку доступних.
// + Заповнює форму Замовлення, вказуючи паспортні дані, термін оренди.
// + Клієнт оплачує Замовлення.
// + Адміністратор реєструє повернення автомобіля.
// + У разі пошкодження Автомобіля Адміністратор вносить інформацію і виставляє рахунок за ремонт.
// + Адміністратор може відхилити Заявку, вказавши причини відмови.

// ==== Додатково ====
// - Категоріі автомобілей
// + Пагінація сторінок
// - DAO слой
// - Міграція БД
// - Безпека (Security) для адміна
// - Зробити версію для Ангуляр


	public static void main(String[] args) {
		SpringApplication.run(RentACarApplication.class, args);
	}
}
