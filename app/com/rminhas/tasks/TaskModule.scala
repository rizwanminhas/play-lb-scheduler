package com.rminhas.tasks

import play.api.inject.SimpleModule
import play.api.inject._

class TasksModule extends SimpleModule(bind[CodeBlockTask].toSelf.eagerly())
