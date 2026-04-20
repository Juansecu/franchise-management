resource "render_web_service" "web" {
  name = var.web_service_name
  plan = "free"
  region = "ohio"

  runtime_source = {
    image = {
      image_url = "ghcr.io/juansecu/franchise-management"
      tag = "latest"
    }
  }

  env_vars = {
    "POSTGRES_DATABASE_URL" = { value = "jdbc:${render_postgres.postgres_instance.connection_info.internal_connection_string}" },
    "SPRING_PROFILES_ACTIVE" = { value = "prod" }
  }

  notification_override = {
    notifications_to_send = "failure"
    preview_notifications_enabled = "false"
  }

  log_stream_override = {
    setting = "drop"
  }
}
