/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ksp.processor

import org.jetbrains.kotlin.ksp.*
import org.jetbrains.kotlin.ksp.processing.Resolver
import org.jetbrains.kotlin.ksp.symbol.KSDeclaration
import org.jetbrains.kotlin.ksp.symbol.KSNode
import org.jetbrains.kotlin.ksp.visitor.KSTopDownVisitor

class DeclarationUtilProcessor : AbstractTestProcessor() {
    private val result = mutableListOf<String>()

    override fun toResult(): List<String> {
        return result
    }

    override fun process(resolver: Resolver) {
        val visitor = DeclarationCollector()
        resolver.getAllFiles().map { it.accept(visitor, result) }
    }
}

class DeclarationCollector : KSTopDownVisitor<MutableCollection<String>, Unit>() {
    override fun defaultHandler(node: KSNode, data: MutableCollection<String>) {
    }

    override fun visitDeclaration(declaration: KSDeclaration, data: MutableCollection<String>) {
        data.add("${declaration.qualifiedName?.asString() ?: "<simple name: ${declaration.simpleName.asString()}>"}: ${declaration.isInternal()}: ${declaration.isLocal()}: ${declaration.isPrivate()}: ${declaration.isProtected()}: ${declaration.isPublic()}: ${declaration.isOpen()}")
    }
}