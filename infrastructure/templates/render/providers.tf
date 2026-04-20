terraform {
  required_providers {
    render = {
      source  = "render-oss/render"
      version = "1.8.0"
    }
  }
}

provider "render" {
  wait_for_deploy_completion = true
}
