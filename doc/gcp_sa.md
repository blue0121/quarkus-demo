使用 Terraform 将服务账号密钥文件导出到 Google Secret Manager，并让 GCP 上的 Java 应用程序读取该密钥文件，可以通过以下步骤实现：

---

### **1. 用 Terraform 创建服务账号和密钥并存储到 Secret Manager**

#### **Terraform 配置文件**
以下是一个完整的 Terraform 配置文件示例：

##### `main.tf`
```hcl
provider "google" {
  project = var.project_id
  region  = var.region
}

# 创建服务账号
resource "google_service_account" "pubsub_sa" {
  account_id   = "pubsub-service-account"
  display_name = "PubSub Service Account"
}

# 为服务账号生成密钥
resource "google_service_account_key" "pubsub_sa_key" {
  service_account_id = google_service_account.pubsub_sa.name
}

# 将密钥存储到 Secret Manager
resource "google_secret_manager_secret" "pubsub_sa_secret" {
  secret_id = "pubsub-service-account-key"
  replication {
    automatic = true
  }
}

resource "google_secret_manager_secret_version" "pubsub_sa_secret_version" {
  secret      = google_secret_manager_secret.pubsub_sa_secret.id
  secret_data = google_service_account_key.pubsub_sa_key.private_key
}

# 输出服务账号邮箱（方便调试）
output "service_account_email" {
  value = google_service_account.pubsub_sa.email
}
```

##### `variables.tf`
```hcl
variable "project_id" {
  description = "The ID of the Google Cloud project"
}

variable "region" {
  description = "The region for resources"
  default     = "us-central1"
}
```

##### `terraform.tfvars`
```hcl
project_id = "your-project-id"
region     = "us-central1"
```

#### **执行 Terraform**
1. 初始化 Terraform：
   ```bash
   terraform init
   ```

2. 检查配置：
   ```bash
   terraform plan
   ```

3. 应用配置：
   ```bash
   terraform apply
   ```

运行后，服务账号密钥会被安全地存储到 Google Secret Manager 中。

---

### **2. 给运行 Java 应用的服务账号访问 Secret Manager 的权限**

默认情况下，运行在 GCP 的 Java 应用程序会使用绑定到实例的服务账号（Compute Engine 或 GKE 的默认服务账号）。需要为此服务账号赋予访问 Secret Manager 的权限。

#### **赋予 Secret Manager 访问权限**
运行以下命令：
```bash
gcloud secrets add-iam-policy-binding pubsub-service-account-key \
  --member="serviceAccount:<SERVICE_ACCOUNT_EMAIL>" \
  --role="roles/secretmanager.secretAccessor"
```

将 `<SERVICE_ACCOUNT_EMAIL>` 替换为运行 Java 应用程序的服务账号。

---

### **3. 在 Java 应用程序中读取密钥文件**

Java 应用程序可以使用 Google Secret Manager 客户端库读取密钥文件，并将其加载为 `GoogleCredentials`。

#### **添加依赖**
在 `pom.xml` 中添加 Google Secret Manager 的依赖：
```xml
<dependency>
    <groupId>com.google.cloud</groupId>
    <artifactId>google-cloud-secretmanager</artifactId>
    <version>2.12.3</version> <!-- 使用最新版本 -->
</dependency>
```

#### **读取 Secret Manager 中的密钥**
以下是 Java 代码示例：

```java
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretPayload;

import java.io.ByteArrayInputStream;

public class SecretManagerExample {

    public static void main(String[] args) {
        String projectId = "your-project-id"; // 替换为您的项目 ID
        String secretId = "pubsub-service-account-key"; // Secret 的名称
        String versionId = "latest"; // 读取最新版本

        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            // 构建访问请求
            AccessSecretVersionRequest request = AccessSecretVersionRequest.newBuilder()
                    .setName(String.format("projects/%s/secrets/%s/versions/%s", projectId, secretId, versionId))
                    .build();

            // 访问密钥
            SecretPayload payload = client.accessSecretVersion(request).getPayload();

            // 加载服务账号密钥到 GoogleCredentials
            String serviceAccountKey = payload.getData().toStringUtf8();
            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream(serviceAccountKey.getBytes())
            ).createScoped("https://www.googleapis.com/auth/cloud-platform");

            System.out.println("Service account credentials loaded successfully!");

            // 使用 credentials 初始化 Pub/Sub 客户端或其他 Google Cloud 服务
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **4. 注意事项**

1. **密钥管理**：
   - 使用 Google Secret Manager 后，无需在本地存储密钥文件。
   - 仅对需要的服务账号授予访问权限，确保密钥的安全性。

2. **权限最小化**：
   - 为运行 Java 应用程序的服务账号授予最小必要权限（`roles/secretmanager.secretAccessor` 和具体服务权限）。

3. **定期轮换密钥**：
   - 可以通过 Terraform 定期创建新的密钥版本并更新 Secret Manager。

---

通过以上步骤，您可以安全地使用 Terraform 将服务账号密钥导出到 Google Secret Manager，并在 Java 应用程序中动态加载密钥。
