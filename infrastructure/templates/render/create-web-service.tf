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
    "POSTGRES_DATABASE_NAME" = { value = local.postgres_database_name },
    "POSTGRES_HOST" = { value = local.postgres_host },
    "POSTGRES_PASSWORD" = { value = local.postgres_password },
    "POSTGRES_PORT" = { value = "5432" },
    "POSTGRES_USER" = { value = local.postgres_user }
  }

  notification_override = {
    notifications_to_send = "failure"
    preview_notifications_enabled = "false"
  }

  log_stream_override = {
    setting = "drop"
  }
}

locals {
  postgres_connection_string = render_postgres.postgres_instance.connection_info.internal_connection_string

  postgres_database_name = regex(
    "^postgresql://[^:]+:[^@]+@[^/]+/([^?]+)$",
    local.postgres_connection_string
  )[0]

  postgres_host = regex(
    "^postgresql://[^:]+:[^@]+@([^/]+)/",
    local.postgres_connection_string
  )[0]

  postgres_password = regex(
    "^postgresql://[^:]+:([^@]+)@",
    local.postgres_connection_string
  )[0]

  postgres_user = regex(
    "^postgresql://([^:]+):[^@]+@",
    local.postgres_connection_string
  )[0]
}
