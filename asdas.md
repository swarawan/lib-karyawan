Membuat Library Java, Publish Menggunakan Github dan Jitpack
Gambar tidak ada kaitannya dengan judul, mon maap~Menggunakan Dependency Management seperti Maven mapun Gradle adalah hal yang sudah umum digunakan oleh pengembang aplikasi. Mereka memungkinkan kita menggunakan satu atau lebih library tanpa harus ribet mencarinya di Google kemudian download jar file-nya, kemudian copy-paste kedalam folder lib di project kita. Kebayang dong ribetnya…
Pernah nggak kepikiran untuk membuat library kita sendiri? Misalnya kita membuat aplikasi listing postal-area se-Indonesia. Kemudian kita publish sehingga orang-orang yang mungkin membutuhkan library kita bisa mereka unduh sendiri via Dependency Management apapun yang mereka pakai.
Yang suka main project-an, atau di kantornya dituntut untuk sering membuat aplikasi baru. Pernah merasa bahwa dalam inisiasi pembuatan project, kita sudah punya boilerplate atau konfigurasi-konfigurasi minimum, dan ketika dihadapkan project baru, kita harus copy-paste terus menerus untuk menggunakan konfigurasi yang sudah kita setting sebelumnya.
Atau contoh kasus lain.
Kita sedang membuat Microservices project. Sudah pasti kita membuat lebih dari satu aplikasi, nah kita pingin setiap aplikasi yang kita buat menggunakan konfigurasi yang sama persis. Daripada seperti kasus diatas, kita copy-paste terus menerus. Alangkah baiknya kita membuat satu project baru yang isinya konfigurasi-konfigurasi dasar yang digunakan dalam project, kita buat project ini sebagai library sehingga akan menghasilkan jar file yang akan digunakan oleh aplikasi-aplikasi utama lain sehingga setiap aplikasi menggunakan library yang sama persis.
Github dan Jitpack
Github, layanan repository gratis yang terkenal dikalangan developer itu. Sederhananya, source code akan kita simpan di Github. Kemudian kita release source code kita.
Selanjutnya, menggunakan Jitpack untuk me-release library kita ke beberapa dependency management yang di support, seperti Maven, Gradle, SBT, ata Leiningen.
Alat Dan Bahan
Alat dan bahan dibawah ini adalah yang saya gunakan dalam artikel ini, apabila berbeda, tidak masalah, karena perbedaanlah yang menyatukan kita, tinggal menyesuaikan saja
Intellij IDE
Java 8
Maven

Membuat Project Library
Buat maven project di Intellij.
File - Menu - Project…
Pilih Maven, Pastikan SDK sudah sesuai dengan yg kita inginkan, Next
Isikan nama library dan lokasi source code, standard laah~
Setelah project jadi, hal pertama yang harus kita eksekusi adalah pom.xml. 
<?xml version="1.0" encoding="UTF-8"?>
<project  >
   ...
   <packaging>jar</packaging>
<!-- Optional -->
<dependencies>
   <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.12</version>
      <scope>provided</scope>
   </dependency>
</dependencies>
<build>
   <plugins>
      <plugin>
         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-compiler-plugin</artifactId>
         <configuration>
            <source>8</source>
            <target>8</target>
         </configuration>
      </plugin>
   </plugins>
</build>
</project>
Tambahkan tag packaging, untuk mendefinisikan bahwa ketika kita build project ini, akan menghasilkan binary berbentuk jar file.
Kemudian kalau ingin menambahkan dependency apapun, silakan. Di project ini saya gunakan projectlombok untuk me-reduce boilerplate pada POJO. Opsional, tidak dipakai  juga tidak apa-apa.
Struktur Folder
Dibawah adalah struktur folder dalam contoh aplikasi yang saya buat
com
|-- swarawan
       |-- lib
            |-- model
                 |-- Employee.java
            |-- repository
                 |-- EmployeeRepository.java
            |-- service
                 |-- EmployeeService.java
                 |-- EmployeeServiceImpl.java
Ada 3 folder utama:
Model
Modelling data class yang berisi data-data yang dimiliki oleh Karyawan. Atau lebih dikenal dengan POJO Class.
@Data
@AllArgsConstructor
public class Employee {

   private Long id;
   private String name;
   private String division;
}
Anotasi [at]Data, adalah fitur dari project-lombok, akan men-generate setter-getter data-data diatas.
Anotasi [at]AllArgsConstructor juga fitur dari project-lombok, akan men-generate constructor dengan data diatas sebagai parameternya.
Repository
Merupakan gudang data karyawan.
public class EmployeeRepository {

   public List<Employee> allEmployees() {
      List<Employee> employees = new ArrayList<Employee>();
      employees.add(new Employee(1L, "Nobita", "Tech"));
      employees.add(new Employee(2L, "Doraemon", "Tech"));
      employees.add(new Employee(3L, "Jayen", "Finance"));
      employees.add(new Employee(4L, "Suneo", "Finance"));
      employees.add(new Employee(5L, "Sisuka", "Product"));
      employees.add(new Employee(6L, "Pak Guru", "Product"));
      return employees;
   }
}
Service
Merupakan fitur yang dimiliki oleh library tersebut. Berisi 1 interface file sebagai blueprint fitur yang akan kita sediakan.
public interface EmployeeService {

   List<Employee> findAllEmployees();
   Employee findEmployee(Long id);
   String findEmployeeToString(Long id);
}
Dan berikut implementasi dari interface file diatas.
public class EmployeeServiceImpl implements EmployeeService {

   private EmployeeRepository repository;

   public EmployeeServiceImpl() {
      this.repository = new EmployeeRepository();
   }

   public List<Employee> findAllEmployees() {
      return repository.allEmployees();
   }

   public Employee findEmployee(Long id) {
      return repository.allEmployees()
            .stream()
            .filter(employee -> id.equals(employee.getId()))
            .findFirst()
            .orElse(null);
   }

   @Override
   public String findEmployeeToString(Long id) {
      Employee employee = findEmployee(id);
      if (null == employee) {
         return "Data tidak ditemukan";
      }
      return String.format("[%s] %s", employee.getDivision(), employee.getName());
   }
}