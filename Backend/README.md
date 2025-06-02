# User Module - Backend Implementation (Spring Boot)

## ✨ Geliştirici: Emre Akbaş

## 📦 Modül: User Accounts (Kullanıcı Sistemi)

### ✅ Emre Akbaş Tarafından Yapılanlar

#### 1. Kullanıcı Kaydı (Register)
- Endpoint: `POST /api/register`
- Kullanıcının `fullName`, `email`, `password` ile hesap oluşturması sağlandı.
- Email tekilliği kontrol edildi.

#### 2. Kullanıcı Profili
- Endpoint: `GET /api/profile?email=`
- Email'e göre kullanıcı bilgileri JSON olarak döndürülür.

#### 3. Kullanıcı Tercihleri (Preferences)
- Endpoint: `POST /api/preferences`
- Kullanıcı, email adresine bağlı olarak `genre`, `actor`, `director` tercihlerini ekleyebilir.
- Endpoint: `PUT /api/preferences`
- Kullanıcının daha önce eklediği tercih bilgilerini güncelleyebilir.
- Endpoint: `POST /api/preferences/delete`
- Kullanıcı, tercihlerini silebilir.
- Endpoint: `GET /api/preferences?email=`
- Belirli bir kullanıcının tercihleri listelenir.

#### 4. Watchlist ve İzlenen Filmler (WatchedMovies)
- Endpoint: `POST /api/watchlist` → İzlenecek film ekleme
- Endpoint: `PATCH /api/watchlist/{id}/watched?email=` → İzlenmişe işaretleme
- Endpoint: `GET /api/watchlist?email=` → Watchlist’i görüntüleme
- Endpoint: `GET /api/watched?email=` → İzlenmiş filmleri listeleme
- User entity’sine `watchlist` ve `watchedMovies` ilişkileri eklendi

#### 5. Veritabanı Yapısı
- `users`, `user_preferences`, `watchlist_items`, `watched_movies` tabloları tanımlandı.
- JPA ile `@OneToMany` / `@ManyToOne` ilişkileri kuruldu.
- Sonsuz JSON döngüsü `@JsonManagedReference` / `@JsonBackReference` ile engellendi.

#### 6. Postman Testleri
- Tüm endpointler Postman ile test edildi.
- Güncelleme, silme, ekleme, listeleme işlemleri başarılı çalıştı.

#### 7. Yapılandırmalar
- `application.properties` içerisine MySQL bağlantı ayarları yapıldı.
- Spring Boot projesi IntelliJ IDEA üzerinden yapılandırıldı.
- Manuel olarak MySQL kurulumu ve veritabanı oluşturma işlemleri yapıldı.
- `DemoApplication.java` üzerinden sistem başarıyla ayağa kaldırıldı.

---

### 🎯 Atakan’a Devredilen Görevler:
- Geliştirilen `PUT`, `DELETE`, `GET /api/preferences` endpointleri test edildi ✅
- Watchlist ve WatchedMovie modülleri başarıyla entegre edildi ✅

---

## 🔒 Login Sistemi
- Bu modülde yer almamaktadır.
- Login işlemi farklı bir ekip üyesi tarafından geliştirilecektir.

---

## 📊 Kullanılan Teknolojiler
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Postman (test)

---

## 📁 Proje Klasör Yapısı
```
src/
 └── main/
     └── java/
         └── com.example.demo/
             ├── controller/        // UserController
             ├── service/           // UserService
             ├── entity/            // User, UserPreference, WatchlistItem, WatchedMovie
             ├── dto/               // RegisterRequest, PreferenceRequest, PreferenceUpdateRequest, WatchlistRequest
             └── repository/        // UserRepository, WatchlistRepository, WatchedMovieRepository
```

---

## 🔧 Test Edilen Endpointler
- `POST /api/register`
- `GET /api/profile?email=`
- `POST /api/preferences`
- `PUT /api/preferences`
- `POST /api/preferences/delete`
- `GET /api/preferences?email=`
- `POST /api/watchlist`
- `PATCH /api/watchlist/{id}/watched?email=`
- `GET /api/watchlist?email=`
- `GET /api/watched?email=`

